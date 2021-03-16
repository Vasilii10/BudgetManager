package ru.nazarenko.jetbrains.academy.budget;

import ru.nazarenko.jetbrains.academy.budget.domain.ApplicationException;
import ru.nazarenko.jetbrains.academy.budget.domain.BudgetManager;
import ru.nazarenko.jetbrains.academy.budget.infrastructure.AppConfiguration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class BudgetManagerBootstrap {

    private static final Logger LOG = Logger.getLogger(BudgetManagerBootstrap.class.getName());

    public static void main(String[] args) {

        configureLogging();

        LOG.info("Start BudgetManager Application");

        String pathToLoadPurchaseFile = "supply/purchases.txt";
        String pathToPurchaseOutputFile = "supply/purchasesOutput.txt";

        System.setProperty(System.lineSeparator(), ">");


        BudgetManager budgetManager = new BudgetManager(
                new AppConfiguration(
                        pathToLoadPurchaseFile,
                        pathToPurchaseOutputFile
                )
        );

        try {
            budgetManager.startBudgetManagerApplication();
        } catch (ApplicationException | IOException e) {
            System.err.println("Application error! Please, try again." + e.getCause());
        }

    }

    private static void configureLogging() {
        Properties appProperties = new Properties();
        try {
            appProperties.load(new BufferedInputStream(new FileInputStream(
                    "supply/appCoreConfig.properties")));

            FileHandler fileHandler = new FileHandler(appProperties.getProperty("outPathToWrightLog"));
            LOG.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            LOG.setUseParentHandlers(false);  // turn off console logging

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
