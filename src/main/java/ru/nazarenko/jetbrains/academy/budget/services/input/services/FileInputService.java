package ru.nazarenko.jetbrains.academy.budget.services.input.services;

import ru.nazarenko.jetbrains.academy.budget.domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.nazarenko.jetbrains.academy.budget.domain.CategoryManager.defineCategoryByName;
import static ru.nazarenko.jetbrains.academy.budget.infrastructure.AppConfiguration.CURRENCY_SYMBOL;

public class FileInputService implements InputService {
    public static final String FILE_SPLITTER = ":";

    private final String pathToFile;
    private final PurchaseManager purchaseManager;
    private final CategoryManager categoryManager;

    public FileInputService(String pathToFile, PurchaseManager purchaseManager, CategoryManager categoryManager) {
        this.pathToFile = pathToFile;
        this.purchaseManager = purchaseManager;
        this.categoryManager = categoryManager;
    }

    @Override
    public void loadPurchases() throws InputServiceException {
        File file = new File(pathToFile);

        ArrayList<Purchase> allArrayList = new ArrayList<>();

        int position = 0;

        try (Scanner scanner = new Scanner(file)) {

            String name = null;
            double price;
            String category = null;

            while (scanner.hasNext()) {
                String nextLine = scanner.nextLine();

                if (nextLine.startsWith("Balance:")) {
                    String substring = nextLine.substring(nextLine.indexOf(':') + 2);
                    BalanceManager.BALANCE = Double.parseDouble(substring);
                    continue;
                }

                nextLine = nextLine.replace(" ", "_");
                String[] split = nextLine.split(FILE_SPLITTER);

                for (String line : split) {
                    if (position == 0) {
                        name = line;
                        name = name.replace("_", " ");
                        position++;
                    } else if (position == 1) {
                        category = line;
                        position++;
                    } else if (position == 2) {
                        String string = line.substring(line.indexOf(CURRENCY_SYMBOL) + 1);
                        price = Double.parseDouble(string);
                        category = category.replace("_", "");
                        allArrayList.add(new Purchase(name, price, new Category(defineCategoryByName(category), category)));
                        position = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new InputServiceException("File reading error!", e);
        }

        for (Purchase purchase : allArrayList) {
            purchaseManager.addPurchaseWithoutChangingBalance(purchase);
            purchaseManager.addPurchaseByCategory(purchase, purchaseManager, categoryManager);
        }

    }

}
