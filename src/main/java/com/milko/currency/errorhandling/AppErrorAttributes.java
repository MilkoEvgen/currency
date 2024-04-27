package com.milko.currency.errorhandling;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppErrorAttributes extends DefaultErrorAttributes {

    public AppErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        Map<String, Object> errorMap = new HashMap<>();
        Object error = getError(request);

        if (error instanceof ResponseStatusException responseException) {
            errorMap.put("code", responseException.getStatusCode().value());
            errorMap.put("message", responseException.getReason());
            errorAttributes.put("status", responseException.getStatusCode().value());
            errorAttributes.put("errors", errorMap);
        }

        return errorAttributes;
    }
}

