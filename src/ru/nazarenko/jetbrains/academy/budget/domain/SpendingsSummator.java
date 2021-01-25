package ru.nazarenko.jetbrains.academy.budget.domain;

import java.util.ArrayList;
import java.util.Scanner;

public class SpendingsSummator {
    public static final String CURRENCY_SYMBOL = "$";
    public static ArrayList<String> stringArrayList = new ArrayList<>();

    SpendingsSummator() {
    }

    public static void readSpendingsFromConsole() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            stringArrayList.add(scanner.nextLine());
        }
    }

    public void printSpendings() {
        double totalAmount = 0;
        for (String s : stringArrayList) {
            totalAmount += Double.parseDouble(s.substring(s.indexOf(CURRENCY_SYMBOL) + 1));
            System.out.println(s);
        }

        System.out.println("Total: " + CURRENCY_SYMBOL + totalAmount);

    }
}
