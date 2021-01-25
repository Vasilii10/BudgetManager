package ru.nazarenko.jetbrains.academy.budget.domain;

public class Category {
    private final int categoryNumber;
    private final String categoryName;

    public Category(int categoryNumber, String categoryName) {
        this.categoryNumber = categoryNumber;
        this.categoryName = categoryName;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
