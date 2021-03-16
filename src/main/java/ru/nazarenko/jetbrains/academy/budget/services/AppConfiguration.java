package ru.nazarenko.jetbrains.academy.budget.services;

public class AppConfiguration {

    private final String pathToPurchaseLoadingFile;
    private final String pathToPurchaseOutputFile;
    public static final String CURRENCY_SYMBOL = "$";

    public AppConfiguration(String pathToPurchaseLoadingFile, String pathToPurchaseOutputFile) {
        this.pathToPurchaseLoadingFile = pathToPurchaseLoadingFile;
        this.pathToPurchaseOutputFile = pathToPurchaseOutputFile;
    }

    public String getPathToPurchaseLoadingFile() {
        return pathToPurchaseLoadingFile;
    }

    public String getPathToPurchaseOutputFile() {
        return pathToPurchaseOutputFile;
    }
}
