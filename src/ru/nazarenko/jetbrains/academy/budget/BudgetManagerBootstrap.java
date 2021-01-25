package ru.nazarenko.jetbrains.academy.budget;

import ru.nazarenko.jetbrains.academy.budget.domain.ApplicationException;
import ru.nazarenko.jetbrains.academy.budget.domain.BalanceManager;
import ru.nazarenko.jetbrains.academy.budget.domain.BudgetManager;

import java.io.IOException;

public class BudgetManagerBootstrap {

    public static final String PATH = "/Users/nazarenko/Desktop/JetBrains Academy/Budget Manager/Budget Manager/task/purchases.txt";

    public static void main(String[] args) {
        System.setProperty(System.lineSeparator(), ">");
        BalanceManager balanceManager = new BalanceManager(); // // TODO: 17/01/2021 убрать !

        // TODO: 25/01/2021 должен регистрироваться в констуркторе

        BudgetManager budgetManager = new BudgetManager(PATH);

        try {
           budgetManager.startBudgetManagerApplication(balanceManager);
        } catch (ApplicationException | IOException e) {
            System.err.println("Application error! Please, try again." + e.getCause());
        }
    }

}
