package ru.nazarenko.jetbrains.academy.budget.domain;

public class Purchase implements Comparable<Purchase> {
    private final String purchaseName;
    private final double purchasePrice;
    private final Category categoryOfPurchase;

    public Purchase(String purchaseName, double purchasePrice, Category categoryOfPurchase) {
        this.purchaseName = purchaseName;
        this.purchasePrice = purchasePrice;
        this.categoryOfPurchase = categoryOfPurchase;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public String getPurchaseCategory() {
        return categoryOfPurchase.getCategoryName();
    }

    @Override
    public int compareTo(Purchase otherPurchase) {
        return Double.compare(otherPurchase.purchasePrice, purchasePrice);
    }
}
