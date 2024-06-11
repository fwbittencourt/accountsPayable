package com.fwbittencourt.accountspayable.domain.enums;

import lombok.Getter;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
@Getter
public enum EnStatus {
    PAID("Conta paga"),
    PENDING_APPROVAL("Aguardando aprovação para pagamento"),
    APPROVED("Pagamento aprovado"),
    REJECTED("Reprovado"),
    OPEN("Aguardando pagamento"),
    PARTIALLY_PAID("Pago parcialmente"),
    OVERDUE("Pagamento atrasado");

    private final String description;


    EnStatus(final String description) {
        this.description = description;
    }

//    Partially Paid: Parcialmente Pago
//    Overdue: Atrasado (indica que a fatura não foi paga até a data de vencimento)
//    Disputed: Em Disputa (indica que há uma contestação ou problema com a fatura)
//    Scheduled for Payment: Agendado para Pagamento (indica que o pagamento foi programado para uma data futura)
//    Hold: Em Espera (indica que o pagamento está suspenso por algum motivo)
//    Cancelled: Cancelado (indica que a fatura foi anulada)
//    Closed: Fechado (indica que todas as ações necessárias foram concluídas, geralmente após o pagamento)
}
