package com.fwbittencourt.accountspayable.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author <Filipe Bittencourt> on 08/06/24
 */

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conta")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dueDate;

    @Column(name = "data_pagamento")
    private LocalDate paymentDate;

    @Column(name = "valor", nullable = false)
    private BigDecimal value;

    @Column(name = "descricao", length = 255)
    private String description;

    @Column(name = "situacao", nullable = false)
    private String status;
}