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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        final PayableEntry savedPayableEntry = payableEntryRepository.save(payableEntry);
        return payableEntryMapper.toPayableEntryDto(savedPayableEntry);
    }

    public List<PayableEntryDto> listAllAccounts() {
        final List<PayableEntry> payableEntries = payableEntryRepository.findAllPayableEntries();
        log.info("{} Listando todas as contas a pagar. Total de contas", Util.LOG_PREFIX);
        return payableEntries.stream()
            .map(payableEntryMapper::toPayableEntryDto)
            .toList();
    }

    public PayableEntryDto findById(final UUID id) throws PayableEntryNotFoundException {
        Optional<PayableEntry> optionalPayableEntry = payableEntryRepository.findById(id);
        return optionalPayableEntry
            .map(payableEntry -> payableEntryMapper.toPayableEntryDto(payableEntry))
            .orElseThrow(() -> new PayableEntryNotFoundException("Conta não encontrada para este ID."));
//      TODO: Tratar exceção do controller
    }

    public MyPage<PayableEntryDto> findAllByFilters(final LocalDate paymentDate, final LocalDate dueDate,
        final List<EnStatus> status, final String description, final boolean interno, final Pageable pageable) {
        log.info("{} Criando filtros para listar as contas a pagar", Util.LOG_PREFIX);

        final var filter = FilterTbPayableEntryVo.mounterFilter(paymentDate, dueDate, status, description, interno);

        log.info("{} Filtros criados! Montando especificação para criar query.", Util.LOG_PREFIX);

        var payableEntrySpecification = filter.toSpecs();

        return payableEntryRepository.findAllByFilters(payableEntrySpecification, pageable)
            .map(payableEntryMapper::toPayableEntryDto);
    }

    public long loadCsvFile(String content) {
        log.info("{} Importando os dados do arquivo CSV...", Util.LOG_PREFIX);
        var lineCount = 1L;
        try {
            List<PayableEntry> payableEntryDtoToSave = new ArrayList<>();
            String[] rows = content.split("\n");
            for (String row : rows) {
                String[] columns = row.split(",");
                var payableEntryDto = new PayableEntry(
                    null,
                    columns[0],
                    getParseLocalDate(columns[1]),
                    getParseLocalDate(columns[2]),
                    EnStatus.valueOf(columns[3]),
                    ConverterStringToBigDecimal.convertToBigDecimal(columns[4])
                );
                lineCount++;
                payableEntryDtoToSave.add(payableEntryDto);
            }
            log.info("{} {} Registros de conta a pagar foram importados e salvo no banco de dados",
                Util.LOG_PREFIX, lineCount);
            return payableEntryRepository.saveAll(payableEntryDtoToSave);
        } catch (Exception e) {
            var message = String.format("Erro ao processar linha %s. Erro: %s", lineCount, e.getMessage());
            log.error("{} " + message, Util.LOG_PREFIX);
            throw new FileProcessException(message);

        }
    }

    private static LocalDate getParseLocalDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return LocalDate.parse(date);
    }

}
