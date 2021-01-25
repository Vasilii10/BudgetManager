package ru.nazarenko.jetbrains.academy.budget;

import ru.nazarenko.jetbrains.academy.budget.domain.ApplicationException;
import ru.nazarenko.jetbrains.academy.budget.domain.BudgetManager;
import ru.nazarenko.jetbrains.academy.budget.services.AppConfiguration;

import java.io.IOException;

public class BudgetManagerBootstrap {
    public static void main(String[] args) {

        String pathToLoadPurchaseFile = "supply/purchases.txt";
        System.setProperty(System.lineSeparator(), ">");

        AppConfiguration configuration = new AppConfiguration(pathToLoadPurchaseFile);
        BudgetManager budgetManager = new BudgetManager(configuration);

        try {
           budgetManager.startBudgetManagerApplication();
        } catch (ApplicationException | IOException e) {
            System.err.println("Application error! Please, try again." + e.getCause());
        }
    }

}
