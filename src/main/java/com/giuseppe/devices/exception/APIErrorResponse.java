package com.giuseppe.devices.exception;

import java.time.LocalDateTime;

public record APIErrorResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {}