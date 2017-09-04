package com.arcansecurity.skeerel.exception;

/**
 * @author Florian Pradines
 */
public class SkeerelException extends Exception {
    public SkeerelException() {}

    public SkeerelException(String message) {
        super(message);
    }

    public SkeerelException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkeerelException(Throwable cause) {
        super(cause);
    }
}
