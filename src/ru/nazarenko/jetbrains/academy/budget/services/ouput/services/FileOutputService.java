package ru.nazarenko.jetbrains.academy.budget.services.ouput.services;

import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static ru.nazarenko.jetbrains.academy.budget.domain.SpendingsSummator.CURRENCY_SYMBOL;

public class FileOutputService {

    private final String fullPathToLoadingFile;

    public FileOutputService(String fullPathToLoadingFile) {
        this.fullPathToLoadingFile = fullPathToLoadingFile;
    }

    public void createFileInFileSystemByPath(ArrayList<Purchase> purchaseArrayList) {
        File file = new File(fullPathToLoadingFile);
        writePurchasesInFile(purchaseArrayList, file);
    }

    public void writePurchasesInFile(ArrayList<Purchase> purchaseArrayList, File outputFile) {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            for (Purchase purchase : purchaseArrayList) {
                printWriter.println(purchase.getPurchaseName() + " : " +
                        purchase.getPurchaseCategory() + " : " + CURRENCY_SYMBOL + purchase.getPurchasePrice()
                );
            }
        } catch (IOException e) {
            System.err.printf("An exception occurs %s", e.getMessage());
        }
    }

    public void insertBalanceInFile(double balance, File outputFile) {
        try (FileWriter printWriter = new FileWriter(outputFile, true)) {
            printWriter.write("Balance: " + balance);
        } catch (IOException e) {
            System.err.printf("An exception occurs %s", e.getMessage());
        }
    }

}
