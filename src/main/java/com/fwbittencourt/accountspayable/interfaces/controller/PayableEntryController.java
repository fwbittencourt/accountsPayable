package com.fwbittencourt.accountspayable.interfaces.controller;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.service.imp.PayableEntryService;
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
    public ResponseEntity<List<PayableEntryDto>> getAccounts() {
        List<PayableEntryDto> accountListDto = payableEntryService.listAllAccounts();
        return ResponseEntity.ok(accountListDto);
    }
}