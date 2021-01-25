package ru.nazarenko.jetbrains.academy.budget.services.soring.services;

import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;

import java.util.ArrayList;

import static ru.nazarenko.jetbrains.academy.budget.services.AppConfiguration.CURRENCY_SYMBOL;

public class AllPurchasesSorting implements SortingService {


    @Override
    public ArrayList<Purchase> sort(ArrayList<Purchase> purchases) {
        purchases.sort(Purchase::compareTo);

        return purchases;
    }

    /**
     * должен выводить отсортированные ! и скорее всего без параметра
     */
    public void print(ArrayList<Purchase> purchases) {

        if (!purchases.isEmpty()) {
            System.out.println();

            double counter = 0.0d;

            System.out.println("All:");
            for (Purchase purchase : purchases) {
                System.out.println(printPurchaseInList(purchase));
                counter += purchase.getPurchasePrice();
            }
            System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", counter));
        } else {
            System.out.println();
            System.out.println("The purchase list is empty!");
        }


    }

    // FIXME: 22/01/2021 не дуюлировать !
    private static String printPurchaseInList(Purchase purchase) {
        return purchase.getPurchaseName() + CURRENCY_SYMBOL + String.format("%.2f", purchase.getPurchasePrice());
    }


}
