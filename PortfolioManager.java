/**
 * PortfolioManager.java
 *
 * Author: Linus Maina
 * Course: IFT/210
 * Project: Stock Portfolio Application
 * Description: Provides a menu-driven stock brokerage portfolio application.
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class PortfolioManager {
    private ArrayList<TransactionHistory> portfolioList = new ArrayList<TransactionHistory>();
    private Scanner scannerNjaramba = new Scanner(System.in);

    public static void main(String[] args) {
        PortfolioManager managerNjaramba = new PortfolioManager();
        managerNjaramba.runNjaramba();
    }

    /** Starts the application and displays the current date and time. */
    public void runNjaramba() {
        System.out.println("Charles Njaramba Brokerage Account");
        System.out.println("===================================");
        System.out.println("Program executed: " + getCurrentDateTimeNjaramba());
        System.out.println();

        boolean runningNjaramba = true;
        while (runningNjaramba) {
            displayMenuNjaramba();
            int optionNjaramba = readMenuOptionNjaramba();

            switch (optionNjaramba) {
                case 0:
                    runningNjaramba = false;
                    System.out.println("Thank you for using the Stock Portfolio Application.");
                    break;
                case 1:
                    depositCashNjaramba();
                    break;
                case 2:
                    withdrawCashNjaramba();
                    break;
                case 3:
                    buyStockNjaramba();
                    break;
                case 4:
                    sellStockNjaramba();
                    break;
                case 5:
                    displayTransactionHistoryNjaramba();
                    break;
                case 6:
                    displayPortfolioNjaramba();
                    break;
                default:
                    if (optionNjaramba != -1) {
                        System.out.println("Error: Invalid menu option. Please enter a number from 0 to 6.");
                    }
            }
            System.out.println();
        }
        scannerNjaramba.close();
    }

    /** Displays the main menu. */
    public void displayMenuNjaramba() {
        System.out.println("Charles Njaramba Brokerage Account");
        System.out.println();
        System.out.println("0 - Exit");
        System.out.println("1 - Deposit Cash");
        System.out.println("2 - Withdraw Cash");
        System.out.println("3 - Buy Stock");
        System.out.println("4 - Sell Stock");
        System.out.println("5 - Display Transaction History");
        System.out.println("6 - Display Portfolio");
        System.out.println();
        System.out.print("Enter option (0 to 6): ");
    }

    /** Reads and validates the menu selection. */
    public int readMenuOptionNjaramba() {
        if (!scannerNjaramba.hasNextInt()) {
            scannerNjaramba.next();
            System.out.println("Error: Invalid input. Please enter a number from 0 to 6.");
            return -1;
        }
        return scannerNjaramba.nextInt();
    }

    /** Deposits cash into the brokerage account. */
    public void depositCashNjaramba() {
        double amountNjaramba = readPositiveAmountNjaramba("Enter deposit amount: $");
        if (amountNjaramba <= 0) {
            return;
        }

        addTransactionNjaramba("CASH", getCurrentDateNjaramba(), "DEPOSIT",
                amountNjaramba, 1.00);
        System.out.printf("Deposit successful: $%.2f%n", amountNjaramba);
    }

    /** Withdraws cash after checking the available cash balance. */
    public void withdrawCashNjaramba() {
        double amountNjaramba = readPositiveAmountNjaramba("Enter withdrawal amount: $");
        if (amountNjaramba <= 0) {
            return;
        }

        double cashNjaramba = getCashBalanceNjaramba();
        if (amountNjaramba > cashNjaramba) {
            System.out.printf("Error: Insufficient cash. Available cash is $%.2f.%n", cashNjaramba);
            return;
        }

        addTransactionNjaramba("CASH", getCurrentDateNjaramba(), "WITHDRAW",
                -amountNjaramba, 1.00);
        System.out.printf("Withdrawal successful: $%.2f%n", amountNjaramba);
    }

    /** Buys stock after checking that enough cash is available. */
    public void buyStockNjaramba() {
        String tickerNjaramba = readTickerNjaramba("Enter stock ticker: ");
        if (tickerNjaramba.isEmpty()) {
            return;
        }

        double quantityNjaramba = readPositiveAmountNjaramba("Enter quantity: ");
        if (quantityNjaramba <= 0) {
            return;
        }

        double priceNjaramba = readPositiveAmountNjaramba("Enter cost per share: $");
        if (priceNjaramba <= 0) {
            return;
        }

        double totalCostNjaramba = quantityNjaramba * priceNjaramba;
        double cashNjaramba = getCashBalanceNjaramba();

        if (totalCostNjaramba > cashNjaramba) {
            System.out.printf("Error: Insufficient cash. Purchase requires $%.2f, but only $%.2f is available.%n",
                    totalCostNjaramba, cashNjaramba);
            return;
        }

        String dateNjaramba = getCurrentDateNjaramba();
        addTransactionNjaramba(tickerNjaramba, dateNjaramba, "BUY",
                quantityNjaramba, priceNjaramba);
        addTransactionNjaramba("CASH", dateNjaramba, "WITHDRAW",
                -totalCostNjaramba, 1.00);

        System.out.printf("Purchase successful: %.2f shares of %s at $%.2f.%n",
                quantityNjaramba, tickerNjaramba, priceNjaramba);
    }

    /** Sells stock after checking that enough shares are owned. */
    public void sellStockNjaramba() {
        String tickerNjaramba = readTickerNjaramba("Enter stock ticker: ");
        if (tickerNjaramba.isEmpty()) {
            return;
        }

        double quantityNjaramba = readPositiveAmountNjaramba("Enter quantity: ");
        if (quantityNjaramba <= 0) {
            return;
        }

        double priceNjaramba = readPositiveAmountNjaramba("Enter selling price per share: $");
        if (priceNjaramba <= 0) {
            return;
        }

        double ownedNjaramba = getTickerQuantityNjaramba(tickerNjaramba);
        if (quantityNjaramba > ownedNjaramba) {
            System.out.printf("Error: You only own %.2f shares of %s.%n", ownedNjaramba, tickerNjaramba);
            return;
        }

        double totalSaleNjaramba = quantityNjaramba * priceNjaramba;
        String dateNjaramba = getCurrentDateNjaramba();
        addTransactionNjaramba(tickerNjaramba, dateNjaramba, "SELL",
                quantityNjaramba, priceNjaramba);
        addTransactionNjaramba("CASH", dateNjaramba, "DEPOSIT",
                totalSaleNjaramba, 1.00);

        System.out.printf("Sale successful: %.2f shares of %s at $%.2f.%n",
                quantityNjaramba, tickerNjaramba, priceNjaramba);
    }

    /** Displays all transactions in the portfolio list. */
    public void displayTransactionHistoryNjaramba() {
        System.out.println();
        System.out.println("Charles Njaramba Brokerage Account");
        System.out.println("===================================");
        System.out.println();
        System.out.println("Date            Ticker      Quantity      Cost Basis   Trans Type");
        System.out.println("=================================================================");

        if (portfolioList.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (TransactionHistory transactionNjaramba : portfolioList) {
            System.out.println(transactionNjaramba.toPrintNjaramba());
        }
    }

    /** Calculates and displays the current portfolio holdings. */
    public void displayPortfolioNjaramba() {
        Map<String, Double> portfolioNjaramba = new LinkedHashMap<String, Double>();

        for (TransactionHistory transactionNjaramba : portfolioList) {
            String tickerNjaramba = transactionNjaramba.getTickerNjaramba();
            double currentQuantityNjaramba = portfolioNjaramba.containsKey(tickerNjaramba)
                    ? portfolioNjaramba.get(tickerNjaramba) : 0.0;

            double signedQuantityNjaramba = transactionNjaramba.getQtyNjaramba();
            if (transactionNjaramba.getTransTypeNjaramba().equalsIgnoreCase("SELL")) {
                signedQuantityNjaramba = -signedQuantityNjaramba;
            }

            portfolioNjaramba.put(tickerNjaramba,
                    currentQuantityNjaramba + signedQuantityNjaramba);
        }

        System.out.println();
        System.out.println("Portfolio as of: " + getCurrentDateTimeNjaramba());
        System.out.println("====================================");
        System.out.println();
        System.out.println("Ticker       Quantity");
        System.out.println("=====================");

        if (portfolioNjaramba.isEmpty()) {
            System.out.println("No holdings.");
            return;
        }

        for (Map.Entry<String, Double> entryNjaramba : portfolioNjaramba.entrySet()) {
            double quantityNjaramba = entryNjaramba.getValue();
            if (Math.abs(quantityNjaramba) > 0.000001) {
                System.out.printf("%-12s %.2f%n", entryNjaramba.getKey(), quantityNjaramba);
            }
        }
    }

    /** Adds a transaction to the ArrayList. */
    public void addTransactionNjaramba(String tickerNjaramba, String dateNjaramba,
                                       String typeNjaramba, double quantityNjaramba,
                                       double costBasisNjaramba) {
        TransactionHistory transactionNjaramba = new TransactionHistory(
                tickerNjaramba, dateNjaramba, typeNjaramba,
                quantityNjaramba, costBasisNjaramba);
        portfolioList.add(transactionNjaramba);
    }

    /** Returns the current CASH balance. */
    public double getCashBalanceNjaramba() {
        double cashNjaramba = 0.0;
        for (TransactionHistory transactionNjaramba : portfolioList) {
            if (transactionNjaramba.getTickerNjaramba().equalsIgnoreCase("CASH")) {
                cashNjaramba += transactionNjaramba.getQtyNjaramba();
            }
        }
        return cashNjaramba;
    }

    /** Returns the current quantity owned for a stock ticker. */
    public double getTickerQuantityNjaramba(String tickerNjaramba) {
        double quantityNjaramba = 0.0;
        for (TransactionHistory transactionNjaramba : portfolioList) {
            if (transactionNjaramba.getTickerNjaramba().equalsIgnoreCase(tickerNjaramba)) {
                if (transactionNjaramba.getTransTypeNjaramba().equalsIgnoreCase("BUY")) {
                    quantityNjaramba += transactionNjaramba.getQtyNjaramba();
                } else if (transactionNjaramba.getTransTypeNjaramba().equalsIgnoreCase("SELL")) {
                    quantityNjaramba -= transactionNjaramba.getQtyNjaramba();
                }
            }
        }
        return quantityNjaramba;
    }

    /** Reads a positive numeric amount from the user. */
    public double readPositiveAmountNjaramba(String promptNjaramba) {
        System.out.print(promptNjaramba);
        if (!scannerNjaramba.hasNextDouble()) {
            scannerNjaramba.next();
            System.out.println("Error: Please enter a valid positive number.");
            return -1.0;
        }

        double amountNjaramba = scannerNjaramba.nextDouble();
        if (!Double.isFinite(amountNjaramba) || amountNjaramba <= 0) {
            System.out.println("Error: Amount must be a finite number greater than zero.");
            return -1.0;
        }
        return amountNjaramba;
    }

    /** Reads and normalizes a stock ticker. */
    public String readTickerNjaramba(String promptNjaramba) {
        System.out.print(promptNjaramba);
        String tickerNjaramba = scannerNjaramba.next();
        tickerNjaramba = tickerNjaramba.toUpperCase();

        if (!tickerNjaramba.matches("[A-Z]{1,10}") || tickerNjaramba.equals("CASH")) {
            System.out.println("Error: Enter a valid stock ticker (1-10 letters), not CASH.");
            return "";
        }
        return tickerNjaramba;
    }

    /** Returns the current date in the required transaction format. */
    public String getCurrentDateNjaramba() {
        DateTimeFormatter formatterNjaramba = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.now().format(formatterNjaramba);
    }

    /** Returns the current date and time. */
    public String getCurrentDateTimeNjaramba() {
        DateTimeFormatter formatterNjaramba = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatterNjaramba);
    }
}
