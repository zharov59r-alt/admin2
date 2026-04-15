package ru.zharov.admin.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException  {
    public ValidationException(String message) {
        super(message);
    }

}
