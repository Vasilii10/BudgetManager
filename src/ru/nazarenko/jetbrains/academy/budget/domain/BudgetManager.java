package ru.nazarenko.jetbrains.academy.budget.domain;

import ru.nazarenko.jetbrains.academy.budget.services.input.services.FileInputService;
import ru.nazarenko.jetbrains.academy.budget.services.input.services.InputService;
import ru.nazarenko.jetbrains.academy.budget.services.input.services.InputServiceException;
import ru.nazarenko.jetbrains.academy.budget.services.ouput.services.FileOutputService;
import ru.nazarenko.jetbrains.academy.budget.services.soring.services.SortServiceContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ru.nazarenko.jetbrains.academy.budget.domain.PurchaseManager.addPurchaseByCategory;
import static ru.nazarenko.jetbrains.academy.budget.domain.PurchaseManager.getAllPurchases;
import static ru.nazarenko.jetbrains.academy.budget.domain.SpendingsSummator.CURRENCY_SYMBOL;
import static ru.nazarenko.jetbrains.academy.budget.services.soring.services.SortServiceContext.sortMenu;

public class BudgetManager {

    public static final String PURCHASE_LIST_IS_EMPTY = "Purchase list is empty!";
    private static final String FORMAT = "$%.2f";
    private final String PATH_TO_LOADING_PURCHASES;
    private final static Map<Integer, String> menuItems = new HashMap<>();

    static {
        menuItems.put(1, " Add income \n");
        menuItems.put(2, " Add purchase \n");
        menuItems.put(3, " Show list of purchases \n");
        menuItems.put(4, " Balance \n");
        menuItems.put(5, " Save \n");
        menuItems.put(6, " Load \n");
        menuItems.put(7, " Analyze (Sort) \n");
        menuItems.put(0, " Exit ");
    }

    public BudgetManager(String path_to_loading_purchases) {
        PATH_TO_LOADING_PURCHASES = path_to_loading_purchases;
    }

    public void startBudgetManagerApplication(BalanceManager balanceManager) throws ApplicationException, IOException {
        showConsoleMenu();

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
                    System.out.println();
                    while (true) {
                        CategoryManager.showCategoriesMenu();
                        int categoryIndexFromConsole = Integer.parseInt(getLineFromConsole(reader));

                        if (categoryIndexFromConsole == 5) { // послений пункт меню
                            System.out.println();
                            break;
                        }
                        Category purchaseCategory = CategoryManager.defineCategoryFromInputBy(categoryIndexFromConsole);

                        Purchase purchase = getPurchaseFromConsole(purchaseCategory, reader);

                        purchaseManager.addPurchase(purchase, balanceManager);

                        addPurchaseByCategory(purchase, purchaseManager, categoryManager);

                        System.out.println("Purchase was added!\n");
                    }
                    break;

                case 3:
                    System.out.println();
                    if (!PurchaseManager.purchasesListIsAvailable()) {
                        System.out.println(PURCHASE_LIST_IS_EMPTY + "\n"); // introduce the list of constants
                        break;
                    }

                    while (true) {
                        CategoryManager.printAllCategoriesToConsole();

                        int choice = Integer.parseInt(reader.readLine());

                        if (choice == 6) { // индекс объявить и назвать
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
                    FileOutputService fileOutputService = new FileOutputService(PATH_TO_LOADING_PURCHASES); // TODO: 25/01/2021 интерфейс!
                    fileOutputService.createFileInFileSystemByPath(getAllPurchases());
                    fileOutputService.insertBalanceInFile(balanceManager.getBalance(), new File(PATH_TO_LOADING_PURCHASES));

                    System.out.println();
                    System.out.println("Purchases were saved!");
                    System.out.println();
                    break;

                case 6:
                    InputService service = new FileInputService(PATH_TO_LOADING_PURCHASES, purchaseManager, categoryManager);
                    service.loadPurchases();

                    System.out.println();
                    System.out.println("Purchases were loaded!");
                    System.out.println();
                    break;

                case 7:
                    SortServiceContext sortServiceContext = new SortServiceContext();
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        sortMenu();
                        int choice = scanner.nextInt();
                        if (choice == 4) {
                            break;
                        } else {
                            sortServiceContext.defineMenuItem(choice);
                            sortServiceContext.setPurchasesForAlgo(PurchaseManager.getAllPurchases());
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
                    System.err.println("Wrong number! Please try again!");
                    break;
            }
            showConsoleMenu();
            defineAppAction(balanceManager, categoryManager, purchaseManager);
        } catch (IOException | InputServiceException e) {
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
        for (Map.Entry<Integer, String> key : menuItems.entrySet()) {
            if (key.getKey() > 0) System.out.print(key.getKey() + ")" + key.getValue());
        }

        System.out.println("0)" + menuItems.get(0) + "\n" + System.getProperty(System.lineSeparator()));
    }

}