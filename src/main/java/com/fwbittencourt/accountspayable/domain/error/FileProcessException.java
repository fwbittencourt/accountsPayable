package com.fwbittencourt.accountspayable.domain.error;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
public class FileProcessException extends RuntimeException {
    public FileProcessException(String message) {
        super(message);
    }
}
