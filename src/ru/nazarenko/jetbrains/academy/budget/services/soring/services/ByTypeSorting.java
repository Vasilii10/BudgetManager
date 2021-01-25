package ru.nazarenko.jetbrains.academy.budget.services.soring.services;

import ru.nazarenko.jetbrains.academy.budget.domain.CategoryManager;
import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;

import java.util.ArrayList;

import static ru.nazarenko.jetbrains.academy.budget.domain.CategoryManager.CATEGORIES_LIST;
import static ru.nazarenko.jetbrains.academy.budget.domain.SpendingsSummator.CURRENCY_SYMBOL;


public class ByTypeSorting implements SortingService {
    @Override
    public ArrayList<Purchase> sort(ArrayList<Purchase> purchases) {
        purchases.sort(Purchase::compareTo);

        return purchases;
    }

    @Override
    public void print(ArrayList<Purchase> purchases) {

        ArrayList<Purchase> foodCategoryList = new ArrayList<>();
        ArrayList<Purchase> ClothesCategoryList = new ArrayList<>();
        ArrayList<Purchase> EntertainmentCategoryList = new ArrayList<>();
        ArrayList<Purchase> OtherCategoryList = new ArrayList<>();


        for (Purchase purchase : purchases
        ) {
            {
                if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(0).getCategoryName())) { // лучше наверное здесь сравнивать объекты класса Categoty
                    foodCategoryList.add(purchase);
                } else if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(1).getCategoryName())) {
                    ClothesCategoryList.add(purchase);
                } else if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(2).getCategoryName())) {
                    EntertainmentCategoryList.add(purchase);
                } else if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(3).getCategoryName())) {
                    OtherCategoryList.add(purchase);
                } else
                    System.err.println("Something went wrong");
            }
        }

        foodCategoryList.sort(Purchase::compareTo);
        ClothesCategoryList.sort(Purchase::compareTo);
        EntertainmentCategoryList.sort(Purchase::compareTo);
        OtherCategoryList.sort(Purchase::compareTo);

        double foodCategoryAllPrice = 0.0d;
        double entertainmentCategoryAllPrice = 0.0d;
        double clothersAllPrice = 0.0d;
        double othersCategoryAllPrice = 0.0d;

        for (Purchase p : foodCategoryList) {
            foodCategoryAllPrice += p.getPurchasePrice();
        }

        for (Purchase p : EntertainmentCategoryList) {
            entertainmentCategoryAllPrice += p.getPurchasePrice();
        }
        for (Purchase p : ClothesCategoryList) {
            clothersAllPrice += p.getPurchasePrice();
        }

        for (Purchase p : OtherCategoryList) {
            othersCategoryAllPrice += p.getPurchasePrice();
        }

        System.out.println();
        System.out.println("Types:");
        System.out.println(CategoryManager.CATEGORIES_LIST.get(0).getCategoryName() + " - " + CURRENCY_SYMBOL + String.format("%.2f", foodCategoryAllPrice));
        System.out.println(CategoryManager.CATEGORIES_LIST.get(2).getCategoryName() + " - " + CURRENCY_SYMBOL + String.format("%.2f", entertainmentCategoryAllPrice));
        System.out.println(CategoryManager.CATEGORIES_LIST.get(1).getCategoryName() + " - " + CURRENCY_SYMBOL + String.format("%.2f", clothersAllPrice));

        System.out.println(CategoryManager.CATEGORIES_LIST.get(3).getCategoryName() + " - " + CURRENCY_SYMBOL + String.format("%.2f", othersCategoryAllPrice));

        double finalSum = foodCategoryAllPrice + entertainmentCategoryAllPrice + clothersAllPrice + othersCategoryAllPrice;

        System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", finalSum));

    }
}
