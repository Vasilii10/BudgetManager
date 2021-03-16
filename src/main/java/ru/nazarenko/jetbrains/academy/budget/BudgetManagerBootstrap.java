package ru.nazarenko.jetbrains.academy.budget;

import ru.nazarenko.jetbrains.academy.budget.domain.ApplicationException;
import ru.nazarenko.jetbrains.academy.budget.domain.BudgetManager;
import ru.nazarenko.jetbrains.academy.budget.services.AppConfiguration;

import java.io.IOException;

public class BudgetManagerBootstrap {
    public static void main(String[] args) {

        String pathToLoadPurchaseFile = "supply/purchases.txt";
        String pathToPurchaseOutputFile = "supply/purchasesOutput.txt";

        System.setProperty(System.lineSeparator(), ">");


        BudgetManager budgetManager = new BudgetManager(
                new AppConfiguration(
                        pathToLoadPurchaseFile,
                        pathToPurchaseOutputFile
                )
        );

        try {
           budgetManager.startBudgetManagerApplication();
        } catch (ApplicationException | IOException e) {
            System.err.println("Application error! Please, try again." + e.getCause());
        }
    }

}
