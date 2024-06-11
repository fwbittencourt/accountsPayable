package com.fwbittencourt.accountspayable.domain.model;

import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PayableEntry {
    private UUID id;
    private String description;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private EnStatus status;
    private BigDecimal value;
}