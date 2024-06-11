package com.fwbittencourt.accountspayable.domain.enums;

import lombok.Getter;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
@Getter
public enum EnStatus {
    OPEN(10, "Aguardando pagamento"),
    PENDING_APPROVAL_OVERDUE(20,"Aguardando aprovação para pagamento"),
    APPROVED(30,"Pagamento aprovado"),
    PAID(40,"Conta paga"),
    CANCELLED(50,"Conta cancelada");

    private final int code;
    private final String description;

    EnStatus(int code, final String description) {
        this.code = code;
        this.description = description;
    }
}
