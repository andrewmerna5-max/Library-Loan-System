package libraryloan;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String FULL_NAME = "Emmanuella Andrew";
    private static final String REGISTRATION_NUMBER = "VU-BIT-2511-1124-DAY";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoanManager manager = new LoanManager();

        System.out.println("Library Loan Management System");
        System.out.println("Name: " + FULL_NAME);
        System.out.println("Registration number: " + REGISTRATION_NUMBER);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = InputHelper.readMenuChoice(scanner);
            try {
                switch (choice) {
                    case 1:
                        addBookLoan(scanner, manager);
                        break;
                    case 2:
                        addLaptopLoan(scanner, manager);
                        break;
                    case 3:
                        displayAllLoans(manager);
                        break;
                    case 4:
                        findLoan(scanner, manager);
                        break;
                    case 5:
                        removeLoan(scanner, manager);
                        break;
                    case 6:
                        displayTotals(manager);
                        break;
                    case 7:
                        running = false;
                        System.out.println("Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid menu choice. Choose 1 to 7.");
                }
            } catch (InvalidLoanException exception) {
                System.out.println("Loan error: " + exception.getMessage());
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("1. Add a BookLoan");
        System.out.println("2. Add a LaptopLoan");
        System.out.println("3. Display all loans");
        System.out.println("4. Find a loan by ID");
        System.out.println("5. Remove a loan");
        System.out.println("6. Display total charges and discounts");
        System.out.println("7. Exit");
    }

    private static void addBookLoan(Scanner scanner, LoanManager manager)
            throws InvalidLoanException {
        String name = InputHelper.readNonBlank(scanner, "Enter borrower name: ");
        boolean firstTime = InputHelper.readYesNo(
                scanner, "Is this a first-time borrower? (yes/no): ");
        int daysLate = InputHelper.readNonNegativeInt(
                scanner, "Enter number of days late: ");
        Loan loan = new BookLoan(manager.generateNextId(), name, daysLate, firstTime);
        manager.addLoan(loan);
        System.out.println();
        System.out.println("Book loan added successfully!");
        System.out.println("Loan ID: " + loan.getLoanId());
        System.out.println("Borrower: " + loan.getCustomerName());
        System.out.printf(Locale.US, "Fine before discount: UGX %,.0f%n",
                loan.calculateGrossCharge());
        System.out.printf(Locale.US, "Discount: UGX %,.0f%n",
                loan.calculateDiscountAmount());
        System.out.printf(Locale.US, "Final charge: UGX %,.0f%n",
                loan.calculateCharge());
    }

    private static void addLaptopLoan(Scanner scanner, LoanManager manager)
            throws InvalidLoanException {
        String name = InputHelper.readNonBlank(scanner, "Customer name: ");
        int daysLate = InputHelper.readNonNegativeInt(scanner, "Days late: ");
        boolean firstTime = InputHelper.readYesNo(
                scanner, "First-time borrower? (yes/no): ");
        Loan loan = new LaptopLoan(
                manager.generateNextId(), name, daysLate, firstTime);
        manager.addLoan(loan);
        System.out.println("Laptop loan added: " + loan.getLoanId());
    }

    private static void displayAllLoans(LoanManager manager) {
        if (manager.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }
        List<Loan> loans = manager.getLoansSortedByCustomerName();
        for (Loan loan : loans) {
            System.out.println(loan);
        }
    }

    private static void findLoan(Scanner scanner, LoanManager manager) {
        String loanId = InputHelper.readNonBlank(scanner, "Loan ID: ");
        Loan loan = manager.findLoan(loanId);
        if (loan == null) {
            System.out.println("No loan exists with ID " + loanId + ".");
        } else {
            System.out.println(loan);
        }
    }

    private static void removeLoan(Scanner scanner, LoanManager manager)
            throws InvalidLoanException {
        String loanId = InputHelper.readNonBlank(scanner, "Loan ID: ");
        Loan loan = manager.findLoan(loanId);
        if (loan == null) {
            System.out.println("No loan exists with ID " + loanId + ".");
            return;
        }
        boolean confirmed = InputHelper.readYesNo(
                scanner, "Remove " + loan.getCustomerName() + "'s loan? (yes/no): ");
        if (confirmed) {
            manager.removeLoan(loanId);
            System.out.println("Loan removed.");
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    private static void displayTotals(LoanManager manager) {
        System.out.printf(Locale.US, "Total charges after discounts: UGX %.2f%n",
                manager.calculateTotalCharges());
        System.out.printf(Locale.US, "Total discounts given: UGX %.2f%n",
                manager.calculateTotalDiscounts());
    }
}
