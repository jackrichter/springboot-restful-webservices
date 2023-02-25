package net.javaguides.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceNme;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourceNme, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceNme, fieldName, fieldValue));
        this.resourceNme = resourceNme;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
