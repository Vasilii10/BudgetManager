package ru.nazarenko.jetbrains.academy.budget.domain;

public class IncorrectMenuItemExeption extends Exception {
    public IncorrectMenuItemExeption() {
    }

    public IncorrectMenuItemExeption(String message) {
        super(message);
    }
}
