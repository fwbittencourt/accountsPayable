package com.fwbittencourt.accountspayable.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Getter
@Setter
public class PayableEntry {
    private UUID id;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private BigDecimal value;

    private String description;

    private String status;
}