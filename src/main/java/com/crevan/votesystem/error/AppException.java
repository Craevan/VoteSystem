package com.crevan.votesystem.error;

import org.springframework.lang.NonNull;

public class AppException extends RuntimeException {
    public AppException(@NonNull final String message) {
        super(message);
    }
}
