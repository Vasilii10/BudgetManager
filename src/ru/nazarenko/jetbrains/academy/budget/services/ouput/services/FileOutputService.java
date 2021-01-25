package ru.nazarenko.jetbrains.academy.budget.services.ouput.services;

import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;
import ru.nazarenko.jetbrains.academy.budget.domain.PurchaseManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.nazarenko.jetbrains.academy.budget.services.AppConfiguration.CURRENCY_SYMBOL;

public class FileOutputService implements OutputService {

    private final String fullPathToOutputFile;
    private final PurchaseManager purchaseManager;

    public FileOutputService(String fullPathToOutputFile, PurchaseManager purchaseManager) {
        this.fullPathToOutputFile = fullPathToOutputFile;
        this.purchaseManager = purchaseManager;

    }

    public void writePurchasesInFile( File outputFile) {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            for (Purchase purchase : purchaseManager.getAllPurchases()) {
                printWriter.println(
                        purchase.getPurchaseName() + " : " +
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

    @Override
    public void outputPurchases() {
        File file = new File(fullPathToOutputFile);
        writePurchasesInFile(file);
    }
}
