package com.fwbittencourt.accountspayable.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableEntryDto(
    UUID id,
    LocalDate dueDate,
    LocalDate paymentDate,
    BigDecimal value,
    String description,
    String status
){}
