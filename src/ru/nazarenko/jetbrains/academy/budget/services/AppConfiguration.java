package ru.nazarenko.jetbrains.academy.budget.services;

public class AppConfiguration {

    private final String pathToPurchaseLoadingFile;

    public AppConfiguration(String pathToPurchaseLoadingFile) {
        this.pathToPurchaseLoadingFile = pathToPurchaseLoadingFile;
    }

    public String getPathToPurchaseLoadingFile() {
        return pathToPurchaseLoadingFile;
    }
}
