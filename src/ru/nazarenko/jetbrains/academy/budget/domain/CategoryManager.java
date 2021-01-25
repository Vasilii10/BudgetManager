package ru.nazarenko.jetbrains.academy.budget.domain;

import java.util.ArrayList;


public class CategoryManager {

    public static final ArrayList<Category> CATEGORIES_LIST = new ArrayList<>();
    private final ArrayList<Purchase> foodCategoryList;
    private final ArrayList<Purchase> clothesCategoryList;
    private final ArrayList<Purchase> entertainmentCategoryList;
    private final ArrayList<Purchase> otherCategoryList;

    static {
        CATEGORIES_LIST.add(new Category(1, "Food"));
        CATEGORIES_LIST.add(new Category(2, "Clothes"));
        CATEGORIES_LIST.add(new Category(3, "Entertainment"));
        CATEGORIES_LIST.add(new Category(4, "Other"));
    }

    public CategoryManager() {
        this.foodCategoryList = new ArrayList<>();
        this.clothesCategoryList = new ArrayList<>();
        this.entertainmentCategoryList = new ArrayList<>();
        this.otherCategoryList = new ArrayList<>();
    }

    public static void showCategoriesMenu() {
        System.out.println();
        System.out.println("Choose the type of purchase");

        for (Category category : CATEGORIES_LIST) {
            System.out.println(category.getCategoryNumber() + ") " + category.getCategoryName());
        }

        System.out.println(CATEGORIES_LIST.size() + 1 + ") " + "Back");
        System.out.println(System.getProperty(System.lineSeparator()));
    }

    public static Category defineCategoryFromInputBy(int categoryNumber) {
        Category categoryForOutput = null;

        for (Category category : CATEGORIES_LIST) {
            if (categoryNumber == category.getCategoryNumber()) {
                categoryForOutput = category;
                break;
            }
        }

        return categoryForOutput;
    }

    public static void printAllCategoriesToConsole() {
        System.out.println("Choose the type of purchase");
        for (Category category : CATEGORIES_LIST)
            System.out.println(category.getCategoryNumber() + ") " + category.getCategoryName());

        System.out.println(CATEGORIES_LIST.size() + 1 + ") " + "All");
        System.out.println(CATEGORIES_LIST.size() + 2 + ") " + "Back");
        System.out.println(System.getProperty(System.lineSeparator()));
    }

    public static int defineCategoryByName(String categoryName) {
        int categoryIndex = 0;
        for (Category category : CATEGORIES_LIST) {
            if (category.getCategoryName().equals(categoryName)) {
                categoryIndex = category.getCategoryNumber();
            }
        }

        return categoryIndex;
    }

    public ArrayList<Purchase> getFoodCategoryList() {
        return foodCategoryList;
    }

    public ArrayList<Purchase> getClothesCategoryList() {
        return clothesCategoryList;
    }

    public ArrayList<Purchase> getEntertainmentCategoryList() {
        return entertainmentCategoryList;
    }

    public ArrayList<Purchase> getOtherCategoryList() {
        return otherCategoryList;
    }
}
