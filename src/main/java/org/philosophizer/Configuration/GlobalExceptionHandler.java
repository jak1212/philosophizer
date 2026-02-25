package org.philosophizer.configuration;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        // Don't log broken pipe - client disconnected, nothing we can do
        if (e instanceof ClientAbortException ||
                (e.getCause() instanceof IOException &&
                        e.getCause().getMessage().contains("Broken pipe"))) {
            return ResponseEntity.status(499).build(); // 499 = Client Closed Request
        }
        log.error("Unhandled exception", e);
        return ResponseEntity.status(500).build();
    }


}

