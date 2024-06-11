package com.fwbittencourt.accountspayable.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Objeto não encontrado")
public class PayableEntryNotFoundException extends RuntimeException {
    public <T> PayableEntryNotFoundException(final Object field, final String fieldName) {
        super("Conta a pagar não encontrada: <"
            .concat(fieldName)
            .concat(">: <")
            .concat(field.toString())
            .concat(">"));
    }

    public static <T> Supplier<PayableEntryNotFoundException> with(final Object field,
        final String fieldName) {
        return () -> new PayableEntryNotFoundException(field, fieldName);
    }
}
