package ru.nazarenko.jetbrains.academy.budget.services.input.services;


public class InputServiceException extends Exception {
    public InputServiceException() {
    }

    public InputServiceException(String message) {
        super(message);
    }

    public InputServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
