package com.fwbittencourt.accountspayable.domain.service;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import com.fwbittencourt.accountspayable.domain.error.FileProcessException;
import com.fwbittencourt.accountspayable.domain.error.PayableEntryNotFoundException;
import com.fwbittencourt.accountspayable.domain.mapper.PayableEntryMapper;
import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.domain.repository.MyPage;
import com.fwbittencourt.accountspayable.infra.utils.ConverterStringToBigDecimal;
import com.fwbittencourt.accountspayable.interfaces.entity.vo.FilterTbPayableEntryVo;
import com.fwbittencourt.accountspayable.domain.repository.PayableEntryRepository;
import com.fwbittencourt.accountspayable.infra.utils.Util;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayableEntryService {

    private PayableEntryRepository payableEntryRepository;
    private PayableEntryMapper payableEntryMapper;

    public PayableEntryDto create(PayableEntryDto payableEntryDto) {
        log.info("{} Criando nova entrada para contas a pagar. Descrição: {}",
            Util.LOG_PREFIX, payableEntryDto.description());

        final PayableEntry payableEntry = payableEntryMapper.toPayableEntry(payableEntryDto);
        payableEntry.validate();
        final PayableEntry savedPayableEntry = payableEntryRepository.save(payableEntry);
        return payableEntryMapper.toPayableEntryDto(savedPayableEntry);
    }

    public PayableEntryDto updatePayableEntry(UUID id, PayableEntryDto payableEntryDto) {
        log.info("{} Alterando conta a pagar: ID {}", Util.LOG_PREFIX, id);

        Optional<PayableEntry> optionalPayableEntry = payableEntryRepository.findById(id);
        return optionalPayableEntry.map(atualPayableEntry -> {
                atualPayableEntry.setDescription(payableEntryDto.description());
                atualPayableEntry.setDueDate(payableEntryDto.dueDate());
                atualPayableEntry.setPaymentDate(payableEntryDto.paymentDate());
                atualPayableEntry.setStatusIfValid(payableEntryDto.status());
                atualPayableEntry.setValue(payableEntryDto.value());
                atualPayableEntry.validate();
                return payableEntryMapper.toPayableEntryDto(payableEntryRepository.save(atualPayableEntry));
            })
            .orElseThrow(PayableEntryNotFoundException.with(id, "id"));
    }

    public PayableEntryDto updatePayableEntryStatus(UUID id, EnStatus status) {
        Optional<PayableEntry> optionalPayableEntry = payableEntryRepository.findById(id);
        return optionalPayableEntry.map(payableEntry -> {
            payableEntry.setStatusIfValid(status);
            return payableEntryMapper.toPayableEntryDto(payableEntryRepository.save(payableEntry));
        }).orElseThrow(PayableEntryNotFoundException.with(id, "id"));
    }

    public PayableEntryDto findById(final UUID id) throws PayableEntryNotFoundException {
        log.info("{} Buscando contas a pagar pelo ID: {}", Util.LOG_PREFIX, id);

        Optional<PayableEntry> optionalPayableEntry = payableEntryRepository.findById(id);
        return optionalPayableEntry
            .map(payableEntry -> payableEntryMapper.toPayableEntryDto(payableEntry))
            .orElseThrow(PayableEntryNotFoundException.with(id, "id"));
    }

    public MyPage<PayableEntryDto> findAllByFilters(final LocalDate initialDueDate, final LocalDate finalDueDate,
        final List<EnStatus> status, final String description, final Pageable pageable) {
        log.info("{} Criando filtros para listar as contas a pagar", Util.LOG_PREFIX);

        final var filter = FilterTbPayableEntryVo.buildFilter(initialDueDate, finalDueDate, status, description);

        log.info("{} Filtros criados! Montando especificação para criar query.", Util.LOG_PREFIX);
        var payableEntrySpecification = filter.toSpecs();
        return payableEntryRepository.findAllByFilters(payableEntrySpecification, pageable)
            .map(payableEntryMapper::toPayableEntryDto);
    }

    public long loadCsvFileContent(String content) {
        log.info("{} Processando os dados do arquivo CSV...", Util.LOG_PREFIX);

        var lineCount = 1L;
        try {
            List<PayableEntry> payableEntryDtoToSave = new ArrayList<>();
            String[] rows = content.split("\n");
            for (String row : rows) {
                String[] columns = row.split(",");
                PayableEntry payableEntry = new PayableEntry(
                    null,
                    columns[0].trim(),
                    getParseLocalDate(columns[1]),
                    getParseLocalDate(columns[2]),
                    EnStatus.valueOf(columns[3]),
                    ConverterStringToBigDecimal.convertToBigDecimal(columns[4])
                );
                payableEntry.validate();
                payableEntryDtoToSave.add(payableEntry);
                lineCount++;
            }
            log.info("{} {} Registros de conta a pagar foram importados, salvando-os no banco de dados",
                Util.LOG_PREFIX, lineCount);
            return payableEntryRepository.saveAll(payableEntryDtoToSave);
        } catch (Exception e) {
            var message = String.format("Erro ao processar linha %s. Erro: %s", lineCount, e.getMessage());
            log.error("{} " + message, Util.LOG_PREFIX);
            throw new FileProcessException(message);

        }
    }

    public BigDecimal getTotalPaidAmount(LocalDate initialDate, LocalDate finalDate) {
        log.info("{} Calculando valor total pago no período entre {} e {}",
            Util.LOG_PREFIX, initialDate.toString(), finalDate);
        return payableEntryRepository.sumPaidAmountBetweenDates(initialDate, finalDate);
    }

    private static LocalDate getParseLocalDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return LocalDate.parse(date);
    }
}