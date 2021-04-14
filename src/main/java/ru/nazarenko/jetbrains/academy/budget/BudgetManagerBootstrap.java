package ru.nazarenko.jetbrains.academy.budget;

import ru.nazarenko.jetbrains.academy.budget.domain.*;
import ru.nazarenko.jetbrains.academy.budget.infrastructure.AppConfiguration;

import java.io.*;
import java.util.Properties;
import java.util.logging.*;


public class BudgetManagerBootstrap {

	public static final String PATH_TO_CONFIG = "supply/appCoreConfig.properties";
	public static final String PATH_TO_LOAD_PURCHASE_FILE = "supply/purchases.txt";
	public static final String PATH_TO_PURCHASE_OUTPUT_FILE = "supply/purchasesOutput.txt";
	private static final Logger LOG = Logger.getLogger(BudgetManagerBootstrap.class.getName());

	public static void main(String[] args) {
		try {
			try {
				configureLogging();
			} catch (LoggingConfigurationException e) {
				System.err.println("Error due configuration logging!");
				throw new ApplicationException();
			}

			LOG.info("Start BudgetManager Application");
			System.setProperty(System.lineSeparator(), ">");

			new BudgetManager(
				new AppConfiguration(
					PATH_TO_LOAD_PURCHASE_FILE,
					PATH_TO_PURCHASE_OUTPUT_FILE
				)
			).startBudgetManagerApplication();

		} catch (ApplicationException | IOException e) {
			System.err.println("Application error! Please, try again.");
		}
	}

	private static void configureLogging() throws LoggingConfigurationException {
		Properties appProperties = new Properties();
		try {
			appProperties.load(new BufferedInputStream(new FileInputStream(PATH_TO_CONFIG)));

			FileHandler fileHandler = new FileHandler(appProperties.getProperty("path_to_write_log"));
			LOG.addHandler(fileHandler);

			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			LOG.setUseParentHandlers(false);

		} catch (IOException e) {
			throw new LoggingConfigurationException();
		}
	}
}
