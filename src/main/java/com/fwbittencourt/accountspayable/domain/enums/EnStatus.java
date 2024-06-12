package com.fwbittencourt.accountspayable.domain.enums;

import lombok.Getter;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
@Getter
public enum EnStatus {
    OPEN("Aguardando pagamento"),
    PENDING_APPROVAL_OVERDUE("Aguardando aprovação para pagamento"),
    APPROVED("Pagamento aprovado"),
    REJECTED("Reprovado"),
    PAID("Conta paga"),
    CANCELLED("Conta cancelada");

    private final String description;

    EnStatus(final String description) {
        this.description = description;
    }
}
