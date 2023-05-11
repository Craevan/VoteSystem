package com.crevan.votesystem.error;

import java.sql.Timestamp;

public record SwaggerExceptionInfo(Timestamp timestamp, int status, String error, String path, String message) {
}
