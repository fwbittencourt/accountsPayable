package com.fwbittencourt.accountspayable.domain.error;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
public class FileLoadingException extends RuntimeException {
    public FileLoadingException(String message) {
        super(message);
    }
}
