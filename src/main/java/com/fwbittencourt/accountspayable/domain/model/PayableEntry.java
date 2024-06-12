package com.fwbittencourt.accountspayable.domain.model;

import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
  * Autor: Filipe Bittencourt on 10/06/24
 */
@Getter
@AllArgsConstructor
public class PayableEntry {

    @NotNull(message = "ID não pode ser nulo")
    private UUID id;

    @Setter
    @NotBlank(message = "Descrição não pode ser vazia")
    private String description;

    @Setter
    @NotNull(message = "Data de vencimento não pode ser nula")
    @FutureOrPresent(message = "Data de vencimento não deve ser no passado")
    private LocalDate dueDate;

    private LocalDate paymentDate;

    @NotNull(message = "Status não pode ser nulo")
    private EnStatus status;

    @Setter
    @NotNull(message = "Valor não pode ser nulo")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal value;

    public void validate() {
        List<String> errorList = new ArrayList<>();
        if (description == null || description.isBlank()) {
            errorList.add("Descrição não pode ser vazia");
        }
        if (dueDate == null || dueDate.isBefore(LocalDate.now()) && status != EnStatus.PENDING_APPROVAL_OVERDUE) {
            errorList.add("Data de vencimento não pode ser nula ou no passado");
        }
        if (paymentDate != null && paymentDate.isAfter(LocalDate.now())) {
            errorList.add("Data de pagamento não poder ser no futuro");
        }
        if (paymentDate != null && paymentDate.isAfter(dueDate) && status != EnStatus.PENDING_APPROVAL_OVERDUE) {
            errorList.add("Esta conta está atrasada e precisa de autorização para ser quitada");
        }
        if (status == null) {
            errorList.add("O status não pode ser nulo");
        }
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            errorList.add("O valor deve ser maior que zero");
        }
        if (!errorList.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errorList));
        }
    }

    private boolean isValidatePaymentDate(LocalDate paymentDate) {
        return paymentDate != null && paymentDate.isBefore(dueDate);
    }

    public void setStatusIfValid(EnStatus newStatus) {
        if (newStatus.equals(this.status)) {
            return;
        }

        switch (newStatus) {
            case OPEN:
            case PENDING_APPROVAL_OVERDUE:
            case CANCELLED:
                this.status = newStatus;
                break;
            case APPROVED:
                this.status = newStatus;
                this.setStatusIfValid(EnStatus.PAID);
                break;
            case PAID:
                if (this.status == EnStatus.PENDING_APPROVAL_OVERDUE) {
                    throw new IllegalArgumentException("Esta conta precisa de aprovação para ser paga.");
                }
                if (this.status == EnStatus.CANCELLED) {
                    throw new IllegalArgumentException("Esta conta já está cancelada.");
                }
                LocalDate paymentDateCandidate = LocalDate.now();
                if (isValidatePaymentDate(paymentDateCandidate)) {
                    this.paymentDate = paymentDateCandidate;
                    this.status = EnStatus.PAID;
                } else {
                    this.status = EnStatus.PENDING_APPROVAL_OVERDUE;
                }
                break;
            case REJECTED:
                this.status = EnStatus.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException("Status inválido.");
        }
    }

    public void setPaymentDate(LocalDate paymentDate) {
        if (paymentDate == null || paymentDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de pagamento não pode ser no futuro");
        }
        if (status == EnStatus.PAID || status == EnStatus.CANCELLED) {
            this.paymentDate = paymentDate;
            return;
        }
        if (isValidatePaymentDate(paymentDate)) {
            this.paymentDate = paymentDate;
        } else {
            this.status = EnStatus.PENDING_APPROVAL_OVERDUE;
        }
    }
}