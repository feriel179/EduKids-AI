package tn.esprit.autoloc.webdistribue.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime timestamp, int status, String error, String message, String path,
        Map<String, String> details
) {}
