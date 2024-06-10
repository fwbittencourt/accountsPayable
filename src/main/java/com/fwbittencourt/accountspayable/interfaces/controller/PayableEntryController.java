package com.fwbittencourt.accountspayable.interfaces.controller;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.service.imp.PayableEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/contas")
public class PayableEntryController {
    private PayableEntryService payableEntryService;

    @PostMapping
    @Operation(summary = "Cadastra uma conta a pagar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PayableEntryDto> createAccount(@RequestBody PayableEntryDto payableEntryDto) {
        PayableEntryDto savedAccount = payableEntryService.create(payableEntryDto);
        log.info("Retornando lançamento criado. {}", savedAccount.description());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedAccount.id())
            .toUri();

        return ResponseEntity.created(location).body(savedAccount);
    }

    @GetMapping
    @Operation(summary = "Lista todas as contas a pagar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<PayableEntryDto>> getAccounts() {
        List<PayableEntryDto> accountListDto = payableEntryService.listAllAccounts();
        return ResponseEntity.ok(accountListDto);
    }
}