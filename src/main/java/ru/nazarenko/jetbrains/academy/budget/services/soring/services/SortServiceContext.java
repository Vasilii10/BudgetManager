package ru.nazarenko.jetbrains.academy.budget.services.soring.services;


import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;

import java.util.ArrayList;


public class SortServiceContext {

    private SortingService sortingService;
    private ArrayList<Purchase> purchases;

    public static void showSortMenu() {
        System.out.println();
        System.out.println("What do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back" + "\n" + System.getProperty(System.lineSeparator()));
    }

    public void defineMenuItem(int choice) {
        switch (choice) {
            case 1:
                setSortAlgo(new AllPurchasesSorting());
                break;
            case 2:
                setSortAlgo(new ByTypeSorting());
                break;
            case 3:
                setSortAlgo(new CertainTypeSoring());
                break;
            case 4:
                return;
        }
    }

    private void setSortAlgo(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    public void setPurchasesForAlgo(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void invokeSort() {
        purchases = this.sortingService.sort(purchases);
    }

    public void printSortedPurchaseList() throws SortingServiceException {
        this.sortingService.print(purchases);
    }

}
