package ru.nazarenko.jetbrains.academy.budget.domain;

import ru.nazarenko.jetbrains.academy.budget.infrastructure.AppConfiguration;
import ru.nazarenko.jetbrains.academy.budget.services.input.services.FileInputService;
import ru.nazarenko.jetbrains.academy.budget.services.input.services.InputService;
import ru.nazarenko.jetbrains.academy.budget.services.input.services.InputServiceException;
import ru.nazarenko.jetbrains.academy.budget.services.ouput.services.FileOutputService;
import ru.nazarenko.jetbrains.academy.budget.services.ouput.services.OutputService;
import ru.nazarenko.jetbrains.academy.budget.services.ouput.services.OutputServiceException;
import ru.nazarenko.jetbrains.academy.budget.services.soring.services.SortServiceContext;
import ru.nazarenko.jetbrains.academy.budget.services.soring.services.SortingServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ru.nazarenko.jetbrains.academy.budget.infrastructure.AppConfiguration.CURRENCY_SYMBOL;

public class BudgetManager {

    public static final String PURCHASE_LIST_IS_EMPTY = "Purchase list is empty!";
    private static final String FORMAT = "$%.2f";
    private final String pathToLoadingPurchases;
    private final String pathToOutputPurchases;
    private final static Map<Integer, String> MENU_ITEMS = new HashMap<>();

    static {
        MENU_ITEMS.put(1, " Add income \n");
        MENU_ITEMS.put(2, " Add purchase \n");
        MENU_ITEMS.put(3, " Show list of purchases \n");
        MENU_ITEMS.put(4, " Balance \n");
        MENU_ITEMS.put(5, " Save \n");
        MENU_ITEMS.put(6, " Load \n");
        MENU_ITEMS.put(7, " Analyze (Sort) \n");
        MENU_ITEMS.put(0, " Exit ");
    }

    public BudgetManager(AppConfiguration configuration) {
        this.pathToLoadingPurchases = configuration.getPathToPurchaseLoadingFile();
        this.pathToOutputPurchases = configuration.getPathToPurchaseOutputFile();
    }

    public void startBudgetManagerApplication() throws ApplicationException, IOException {
        showConsoleMenu();

        BalanceManager balanceManager = new BalanceManager();
        CategoryManager categoryManager = new CategoryManager();
        PurchaseManager purchaseManager = new PurchaseManager(categoryManager);
        defineAppAction(balanceManager, categoryManager, purchaseManager);
    }

    private void defineAppAction(BalanceManager balanceManager,
                                 CategoryManager categoryManager,
                                 PurchaseManager purchaseManager) throws IOException, ApplicationException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int menuChoice = Integer.parseInt(reader.readLine());
        try {
            switch (menuChoice) {
                case 1:
                    System.out.println();
                    System.out.println("Enter income: \n" + System.getProperty(System.lineSeparator()));
                    balanceManager.addIncome(getNumberFromConsole(CURRENCY_SYMBOL, reader));
                    System.out.println("Income was added! \n");
                    break;

                case 2:
                    int backItemIndexInCategoriesMenu = 5;

                    System.out.println();
                    while (true) {
                        CategoryManager.showCategoriesMenu();
                        int categoryIndexFromConsole = Integer.parseInt(getLineFromConsole(reader));

                        if (categoryIndexFromConsole == backItemIndexInCategoriesMenu) {
                            System.out.println();
                            break;
                        }
                        Category purchaseCategory = CategoryManager.defineCategoryFromInputBy(categoryIndexFromConsole);

                        Purchase purchase = getPurchaseFromConsole(purchaseCategory, reader);

                        purchaseManager.addPurchase(purchase, balanceManager);
                        purchaseManager.addPurchaseByCategory(purchase, purchaseManager, categoryManager);

                        System.out.println("Purchase was added!\n");
                    }
                    break;

                case 3:
                    System.out.println();
                    int backItemIndexInAllCategoriesMenu = 6;

                    if (!PurchaseManager.purchasesListIsAvailable()) {
                        System.out.println(PURCHASE_LIST_IS_EMPTY + "\n"); // introduce the list of constants
                        break;
                    }

                    while (true) {
                        CategoryManager.printAllCategoriesToConsole();
                        int choice = Integer.parseInt(reader.readLine());

                        if (choice == backItemIndexInAllCategoriesMenu) {
                            System.out.println();
                            break;
                        }
                        System.out.println();
                        purchaseManager.printPurchasesBy(choice);

                        System.out.println();
                    }
                    break;

                case 4:
                    System.out.println();
                    System.out.println("Balance: ");
                    System.out.printf(FORMAT, balanceManager.getBalance());
                    System.out.println("\n");
                    break;

                case 5:
                    OutputService outputService = new FileOutputService(pathToOutputPurchases, purchaseManager);
                    outputService.outputPurchases();

                    System.out.println();
                    System.out.println("Purchases were saved!");
                    System.out.println();
                    break;

                case 6:
                    InputService fileInputService = new FileInputService(pathToLoadingPurchases, purchaseManager, categoryManager);
                    fileInputService.loadPurchases();

                    System.out.println();
                    System.out.println("Purchases were loaded!");
                    System.out.println();
                    break;

                case 7:
                    SortServiceContext sortServiceContext = new SortServiceContext();
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                       SortServiceContext.showSortMenu();
                        int choice = scanner.nextInt();
                        if (choice == 4) {
                            break;
                        } else {
                            sortServiceContext.defineMenuItem(choice);
                            sortServiceContext.setPurchasesForAlgo(purchaseManager.getAllPurchases());
                            sortServiceContext.invokeSort();
                            sortServiceContext.printSortedPurchaseList();
                        }
                    }
                    System.out.println();
                    break;

                case 0:
                    System.out.println();
                    System.out.print("Bye!");
                    System.exit(0);

                default:
                    throw new IncorrectMenuItemExeption();
            }
            showConsoleMenu();
            defineAppAction(balanceManager, categoryManager, purchaseManager);

        } catch (IOException | InputServiceException | IncorrectMenuItemExeption | SortingServiceException | OutputServiceException e) {
            throw new ApplicationException(e);
        }
    }

    private static double getNumberFromConsole(String currencySymbol, BufferedReader reader) throws IOException {
        String readFromConsole = reader.readLine();

        return Double.parseDouble(readFromConsole.substring(readFromConsole.indexOf(currencySymbol) + 1));
    }

    private static String getLineFromConsole(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    private static Purchase getPurchaseFromConsole(Category purchaseCategory, BufferedReader reader) throws IOException {
        System.out.println();
        System.out.println("Enter purchase name:\n" + System.getProperty(System.lineSeparator()));

        String nameOfPurchase = getLineFromConsole(reader);
        System.out.println("Enter its price: \n" + System.getProperty(System.lineSeparator()));

        double purchasePrice = getNumberFromConsole(CURRENCY_SYMBOL, reader);

        return new Purchase(nameOfPurchase, purchasePrice, purchaseCategory);
    }

    private static void showConsoleMenu() {
        for (Map.Entry<Integer, String> key : MENU_ITEMS.entrySet()) {
            if (key.getKey() > 0) System.out.print(key.getKey() + ")" + key.getValue());
        }

        System.out.println("0)" + MENU_ITEMS.get(0) + "\n" + System.getProperty(System.lineSeparator()));
    }

}