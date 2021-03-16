package ru.nazarenko.jetbrains.academy.budget.domain;

import java.util.ArrayList;

import static ru.nazarenko.jetbrains.academy.budget.domain.BudgetManager.PURCHASE_LIST_IS_EMPTY;
import static ru.nazarenko.jetbrains.academy.budget.domain.CategoryManager.CATEGORIES_LIST;
import static ru.nazarenko.jetbrains.academy.budget.infrastructure.AppConfiguration.CURRENCY_SYMBOL;


public class PurchaseManager {
   protected static ArrayList<Purchase> allPurchases = new ArrayList<>();

    private final ArrayList<Purchase> foodCategoryList;
    private final ArrayList<Purchase> ClothesCategoryList;
    private final ArrayList<Purchase> EntertainmentCategoryList;
    private final ArrayList<Purchase> OtherCategoryList;

    public PurchaseManager(CategoryManager categoryManager) {
        foodCategoryList = categoryManager.getFoodCategoryList();
        ClothesCategoryList = categoryManager.getClothesCategoryList();
        EntertainmentCategoryList = categoryManager.getEntertainmentCategoryList();
        OtherCategoryList = categoryManager.getOtherCategoryList();
    }

    public static void printPurchases() {
        for (Purchase purchase : allPurchases)
            System.out.println(printPurchaseInList(purchase));
    }

    public static boolean purchasesListIsAvailable(ArrayList<Purchase> list) {
        return !list.isEmpty();
    }

    public static boolean purchasesListIsAvailable() {
        return !allPurchases.isEmpty();
    }

    public void printPurchasesBy(int categoryIndex) { // TODO: 24/01/2021  refactor this method
        if (categoryIndex == 1) {
            System.out.println("Food:");
            if (foodCategoryList.size() <= 0) { // FIXME: 25/01/2021 и здесь решить уже как
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                for (Purchase purchase : foodCategoryList)
                    System.out.println(printPurchaseInList(purchase));

                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(this.foodCategoryList)));
            }
        } else if (categoryIndex == 2) {

            System.out.println("Clothes:");
            if (!purchasesListIsAvailable(ClothesCategoryList)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                for (Purchase purchase : ClothesCategoryList)
                    System.out.println(printPurchaseInList(purchase));

                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(ClothesCategoryList)));
            }
        } else if (categoryIndex == 3) {
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
            if (!purchasesListIsAvailable(allPurchases)) {
                System.out.println(PURCHASE_LIST_IS_EMPTY);
            } else {
                printPurchases();
                System.out.println("Total sum: " + CURRENCY_SYMBOL + String.format("%.2f", printTotalSumForCategory(allPurchases)));
            }
        } else {
            System.err.println("Wrong!");
        }
    }

    public static String printPurchaseInList(Purchase purchase) {
        return purchase.getPurchaseName() + CURRENCY_SYMBOL + String.format("%.2f", purchase.getPurchasePrice());
    }

    public static double printTotalSumForCategory(ArrayList<Purchase> purchases) {
        double sumOfPurchasesInCategory = 0;
        for (Purchase purchase : purchases)
            sumOfPurchasesInCategory += purchase.getPurchasePrice();

        return sumOfPurchasesInCategory;
    }

    public void addPurchase(Purchase purchase, BalanceManager balanceManager) {
        allPurchases.add(purchase);
        balanceManager.subtractPurchaseFromBalance(purchase.getPurchasePrice());
    }

    // TODO: 10/01/2021 придумать как подавать список категорий
    public void addPurchaseByCategory(Purchase purchase, PurchaseManager purchaseManager, CategoryManager categoryManager) {
        if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(0).getCategoryName())) { // лучше наверное здесь сравнивать объекты класса Categoty
            purchaseManager.getFoodCategoryList().add(purchase);
        } else if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(1).getCategoryName())) {
            purchaseManager.getClothesCategoryList().add(purchase);
        } else if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(2).getCategoryName())) {
            purchaseManager.getEntertainmentCategoryList().add(purchase);
        } else if (purchase.getPurchaseCategory().equals(CATEGORIES_LIST.get(3).getCategoryName())) {
            purchaseManager.getOtherCategoryList().add(purchase);
        } else
            System.err.println("Something went wrong");

    }

    public void addPurchaseWithoutChangingBalance(Purchase purchase) {
        allPurchases.add(purchase);
    }

    public ArrayList<Purchase> getAllPurchases() {
        return allPurchases;
    }

    public ArrayList<Purchase> getFoodCategoryList() {
        return foodCategoryList;
    }

    public ArrayList<Purchase> getClothesCategoryList() {
        return ClothesCategoryList;
    }

    public ArrayList<Purchase> getEntertainmentCategoryList() {
        return EntertainmentCategoryList;
    }

    public ArrayList<Purchase> getOtherCategoryList() {
        return OtherCategoryList;
    }
}
