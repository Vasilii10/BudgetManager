package ru.nazarenko.jetbrains.academy.budget.domain;

import java.util.ArrayList;

public class BalanceManager {
    
    private final ArrayList<Double> incomesList = new ArrayList<>();
    public static double BALANCE;

    public BalanceManager() { }

    public void addIncome(double income) {
        incomesList.add(income);
        BALANCE += income;
    }

    public void subtractPurchaseFromBalance(double purchase) {
        BALANCE -= purchase;
    }

    public ArrayList<Double> getIncomesList() {
        return incomesList;
    }

    public double getBalance() {
        return BALANCE;
    }

    public void setBalance(double balance) {
        this.BALANCE =balance;
    }
}
