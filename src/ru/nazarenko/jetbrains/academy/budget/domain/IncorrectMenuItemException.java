package ru.nazarenko.jetbrains.academy.budget.domain;

public class IncorrectMenuItemException extends Exception {
    public IncorrectMenuItemException() {
    }

    public IncorrectMenuItemException(String message) {
        super(message);
    }
}
