package ru.nazarenko.jetbrains.academy.budget.services.soring.services;

import ru.nazarenko.jetbrains.academy.budget.domain.CategoryManager;
import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;

import java.util.ArrayList;
import java.util.Scanner;

import static ru.nazarenko.jetbrains.academy.budget.domain.BudgetManager.PURCHASE_LIST_IS_EMPTY;
import static ru.nazarenko.jetbrains.academy.budget.domain.CategoryManager.CATEGORIES_LIST;
import static ru.nazarenko.jetbrains.academy.budget.domain.PurchaseManager.*;
import static ru.nazarenko.jetbrains.academy.budget.services.AppConfiguration.CURRENCY_SYMBOL;

public class CertainTypeSoring implements SortingService {
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

        CategoryManager.showCategoriesMenu();
        Scanner scanner = new Scanner(System.in);

        System.out.println();

        int categoryIndex = scanner.nextInt();


        if (categoryIndex == 1) {
            System.out.println("Food:");
            if (!purchasesListIsAvailable(foodCategoryList)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                for (Purchase purchase : foodCategoryList)
                    System.out.println(printPurchaseInList(purchase));

                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(foodCategoryList)));
            }
        } else if (categoryIndex == 3) {

            System.out.println("Clothes:");
            if (!purchasesListIsAvailable(ClothesCategoryList)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                for (Purchase purchase : ClothesCategoryList)
                    System.out.println(printPurchaseInList(purchase));

                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(ClothesCategoryList)));
            }
        } else if (categoryIndex == 2) {
            System.out.println("Entertainment:");
            if (!purchasesListIsAvailable(EntertainmentCategoryList)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                for (Purchase purchase : EntertainmentCategoryList)
                    System.out.println(printPurchaseInList(purchase));

                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(EntertainmentCategoryList)));
            }
        } else if (categoryIndex == 4) {
            System.out.println("Other:");
            if (!purchasesListIsAvailable(OtherCategoryList)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                for (Purchase purchase : OtherCategoryList)
                    System.out.println(printPurchaseInList(purchase));

                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(OtherCategoryList)));
            }
        } else if (categoryIndex == 5) {
            System.out.println("All:");
            if (!purchasesListIsAvailable(purchases)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                printPurchases();
                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(purchases)));
            }
        } else {
            System.err.println("Wrong!");
        }

    }
}
