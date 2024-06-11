package com.fwbittencourt.accountspayable.domain.dto;

import com.fwbittencourt.accountspayable.domain.enums.EnStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableEntryDto(
    UUID id,
    String description,
    LocalDate dueDate,
    LocalDate paymentDate,
    EnStatus status,
    BigDecimal value
){}
