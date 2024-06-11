package com.fwbittencourt.accountspayable.domain.model;

import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

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

    public void validate() {
        List<String> erroList = new ArrayList<>();
        if (description == null || description.isEmpty()) {
            erroList.add("A descrição não pode ser nula ou vazia");
        }
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            erroList.add("Data de vencimento não pode ser nula ou anterior a hoje");
        }
        if (paymentDate != null && paymentDate.isAfter(dueDate)) {
            if (nonNull(status) && !this.status.equals(EnStatus.PENDING_APPROVAL_OVERDUE))
                erroList.add("Esta conta está atrasa e precisá de autorização para ser quitata");
        }
        if (status == null) {
            erroList.add("O status não pode ser nulo");
        }
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            erroList.add("O valor deve ser maior que zero");
        }
        if (!erroList.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", erroList));
        }
    }

    private boolean isValidatePaymentDate(LocalDate paymentDate) {
        return paymentDate != null && paymentDate.isBefore(dueDate);
    }

    public void setStatusIfValid(EnStatus status) {
        var previousStatus = this.status;
        if (status.equals(previousStatus)) {
            setStatus(status);
            return;
        }
        switch (status) {
            case OPEN, PENDING_APPROVAL_OVERDUE:
                setStatus(status);
                break;
            case APPROVED:
                setStatus(EnStatus.PAID);
                setPaymentDate(LocalDate.now());
                break;
            case PAID:
                if (previousStatus.equals(EnStatus.PENDING_APPROVAL_OVERDUE)) {
                    throw new IllegalArgumentException("Esta conta precisa de aprovação para ser paga.");
                }
                LocalDate paymentDateCandidate = LocalDate.now();
                if (isValidatePaymentDate(paymentDateCandidate)) {
                    setPaymentDate(paymentDateCandidate);
                    setStatus(EnStatus.PAID);
                } else {
                    setStatus(EnStatus.PENDING_APPROVAL_OVERDUE);
                }
                break;
            case CANCELLED:
                setStatus(EnStatus.CANCELLED);
                break;
            default:
                throw new IllegalArgumentException("Status inválido.");
        }
    }

    private void setStatus(EnStatus status) {
        this.status = status;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        if (status.equals(EnStatus.PAID) || status.equals(EnStatus.CANCELLED)) {
            this.paymentDate = paymentDate;
            return;
        }
        if (isValidatePaymentDate(paymentDate)) {
            this.paymentDate = paymentDate;
        } else {
            setStatus(EnStatus.PENDING_APPROVAL_OVERDUE);
        }
    }
}


