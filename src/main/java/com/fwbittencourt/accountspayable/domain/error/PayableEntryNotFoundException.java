package com.fwbittencourt.accountspayable.domain.error;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
public class PayableEntryNotFoundException extends Exception {
    public PayableEntryNotFoundException(String message) {
        super(message);
    }
}
