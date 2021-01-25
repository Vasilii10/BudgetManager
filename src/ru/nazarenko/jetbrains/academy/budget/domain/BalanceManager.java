package ru.nazarenko.jetbrains.academy.budget.domain;

import java.util.ArrayList;

public class BalanceManager {
    
    private final ArrayList<Double> incomes = new ArrayList<>();
    public static double balance;

    public BalanceManager() { }

    public void addIncome(double income) {
        incomes.add(income);
        balance += income;
    }

    public void subtractPurchaseFromBalance(double purchase) {
        balance -= purchase;
    }

    public ArrayList<Double> getIncomes() {
        return incomes;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance=balance;
    }
}
