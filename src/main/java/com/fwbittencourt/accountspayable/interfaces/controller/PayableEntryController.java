package com.fwbittencourt.accountspayable.interfaces.controller;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import com.fwbittencourt.accountspayable.domain.error.FileLoadingException;
import com.fwbittencourt.accountspayable.domain.repository.MyPage;
import com.fwbittencourt.accountspayable.domain.service.PayableEntryService;
import com.fwbittencourt.accountspayable.infra.utils.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/contas")
public class PayableEntryController {
    private PayableEntryService payableEntryService;

    @GetMapping
    @Operation(summary = "Listar contas a pagar", description = "Obtêm a lista de contas a pagar, com filtro de data de vencimento situação e descrição;")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MyPage<PayableEntryDto>> getPayableEntryListFilter(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate initialDueDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate finalDueDate,
        @RequestParam(required = false) List<EnStatus> status,
        @RequestParam(required = false) String description,
        Pageable pageable) {
        log.info("{} Chamando serviço para listar contas a pagar por filtro", Util.LOG_PREFIX);

        final MyPage<PayableEntryDto> payableEntryDtoPage = payableEntryService.findAllByFilters(
            initialDueDate, finalDueDate, status, description, pageable);

        return ResponseEntity.ok(payableEntryDtoPage);
    }

    @PostMapping
    @Operation(summary = "Cadastra uma conta a pagar", description = "Veja PayableEntryDto na sessão de Schemas abaixo")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PayableEntryDto> createPayableEntry(@RequestBody PayableEntryDto payableEntryDto) {
        log.info("{} Chamando serviço para criar nova entrada para contas a pagar. Descrição: {}",
            Util.LOG_PREFIX, payableEntryDto.description());

        PayableEntryDto savedPayableEntry = payableEntryService.create(payableEntryDto);
        log.info("{} Conta {} criada com Sucesso!", Util.LOG_PREFIX, savedPayableEntry.description());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPayableEntry.id())
            .toUri();
        return ResponseEntity.created(location).body(savedPayableEntry);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar uma conta a pagar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PayableEntryDto> atualizarConta(@PathVariable UUID id, @RequestBody PayableEntryDto payableEntryDto) {
        log.info("{} Chamando serviço para alterar conta a pagar: ID {}", Util.LOG_PREFIX, id);

        PayableEntryDto payableEntryDtoUpdated = payableEntryService.updatePayableEntry(id, payableEntryDto);
        return ResponseEntity.ok(payableEntryDtoUpdated);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca uma conta a pagar pelo ID")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PayableEntryDto> getPayableEntryById(@PathVariable UUID id) {
        log.info("Chamando serviço para buscar conta a pagar com o ID: {}", id);

        return ResponseEntity.ok(payableEntryService.findById(id));
    }

    @PutMapping("/{id}/situacao")
    @Operation(summary = "Alterar a situação de uma conta a pagar pelo ID", description = "Opções disponíveis OPEN, PENDING_APPROVAL_OVERDUE, PAID, CANCELLED")
    public ResponseEntity<PayableEntryDto> updateStatus(@PathVariable UUID id, @RequestParam String status) {
        log.info("Chamando serviço para alterar a situação da conta a pagar ID: {}", id);

        return ResponseEntity.ok(payableEntryService.updatePayableEntryStatus(id, EnStatus.valueOf(status)));
    }

    @GetMapping("/total-pago")
    @Operation(summary = "Busca o valor total a ser pago baseado em um intervalo de tempo")
    @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<BigDecimal> getTotalPaidAmount(
            @NotNull @RequestParam LocalDate initialDate,
            @NotNull @RequestParam LocalDate finalDate) {

        log.info("Chamando serviço para calcular o valor a pagar no período de {} a {}",
            initialDate.toString(), finalDate.toString());

        BigDecimal totalPaid = payableEntryService.getTotalPaidAmount(initialDate, finalDate);
        return ResponseEntity.ok(totalPaid);
    }

    @Operation(summary = "Importar lote de contas a pagar", description = "não tem cabeçalho; Usa \",\" como delimitador de campos")
    @PostMapping(path = "/importar")
    public ResponseEntity<String> post(@RequestParam("file") MultipartFile file) {
        log.info("{} Recebendo o aquivo para processar em lote. Tamanho: {} bytes",
            Util.LOG_PREFIX, file.getSize());

        if (file.isEmpty()) return ResponseEntity.ok("O arquivo está vazio");

        log.info("{} Chamando serviço para carregar o arquivo CSV", Util.LOG_PREFIX);
        try {
            long linesProcessed = payableEntryService.loadCsvFile(new String(file.getBytes()));
            return ResponseEntity.ok("Arquivo carregado com sucesso. Linhas processadas: " + linesProcessed);
        } catch (IOException e) {
            log.error("{} Erro ao processar o arquivo", Util.LOG_PREFIX);
            throw new FileLoadingException("Erro ao processar o arquivo CSV.");
        }
    }
}