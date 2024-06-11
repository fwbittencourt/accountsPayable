package com.fwbittencourt.accountspayable.interfaces.controller;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import com.fwbittencourt.accountspayable.domain.error.FileLoadingException;
import com.fwbittencourt.accountspayable.domain.error.PayableEntryNotFoundException;
import com.fwbittencourt.accountspayable.domain.repository.MyPage;
import com.fwbittencourt.accountspayable.domain.service.PayableEntryService;
import com.fwbittencourt.accountspayable.infra.utils.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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
    @Operation(summary = "Listar contas a pagar", description = "Lista contas a pagar de acordo com o filtro")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MyPage<PayableEntryDto>> getPayableEntryListFilter(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate paymentDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dueDate,
        @RequestParam(required = false) List<EnStatus> status,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) boolean interno,
        Pageable pageable) {
        log.info("{} Chamando serviço para listar contas a pagar por filtro", Util.LOG_PREFIX);

        final MyPage<PayableEntryDto> payableEntryDtoPage = payableEntryService.findAllByFilters(
            paymentDate, dueDate, status, description, interno, pageable);

        return ResponseEntity.ok(payableEntryDtoPage);
    }
    
    
    @PostMapping
    @Operation(summary = "Cadastra uma conta a pagar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PayableEntryDto> createPayableEntry(@RequestBody PayableEntryDto payableEntryDto) {
        log.info("Chamando serviço para criar nova entrada para contas a pagar. Descrição: {}", payableEntryDto.description());
        PayableEntryDto savedPayableEntry = payableEntryService.create(payableEntryDto);
        log.info("Conta {} criada com Sucesso!", savedPayableEntry.description());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPayableEntry.id())
            .toUri();
        return ResponseEntity.created(location).body(savedPayableEntry);
    }

    @GetMapping(value = "{/id}")
    @Operation(summary = "Busca uma conta a pagar pelo ID")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PayableEntryDto> getPayableEntryById(@NotNull @PathVariable UUID id)
        throws PayableEntryNotFoundException {
        try {
            return ResponseEntity.ok(payableEntryService.findById(id));
        } catch (PayableEntryNotFoundException e) {
            log.error("{} Conta não encontrada para este ID", Util.LOG_PREFIX);
            throw new PayableEntryNotFoundException("Não foi possível encontrar uma conta com esse ID");
        }
    }

    @Operation(summary = "Importar lote de contas a pagar")
    @PostMapping(path = "/importar")
    public ResponseEntity<String> post(@NonNull @RequestParam("file") MultipartFile file) {
        log.info("{} Recebendo o aquivo para processar em lote. Tamanho: {} bytes",
            Util.LOG_PREFIX, file.getSize());

        if (file.isEmpty()) {
            return ResponseEntity.ok("O arquivo está vazio");
        }
        try {
            log.info("{} Chamando serviço para carregar o arquivo CSV", Util.LOG_PREFIX);
            long linesProcessed = payableEntryService.loadCsvFile(new String(file.getBytes()));
            return ResponseEntity.ok("Arquivo carregado com sucesso. Linhas processadas: " + linesProcessed);
        } catch (IOException e) {
            log.error("{} Erro ao processar o arquivo", Util.LOG_PREFIX);
            throw new FileLoadingException("Erro ao processar o arquivo CSV.");
        }
    }
}