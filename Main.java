import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ATMService atmService = new ATMService();

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("       JAVA ATM SYSTEM");
        System.out.println("==================================");

        Account loggedInAccount = authenticateUser();

        if (loggedInAccount == null) {
            System.out.println("\nToo many incorrect login attempts.");
            System.out.println("Your session has been terminated.");
            return;
        }

        System.out.println(
                "\nWelcome, " + loggedInAccount.getAccountHolderName() + "!"
        );

        showATMMenu(loggedInAccount);
    }

    private static Account authenticateUser() {
        int maximumAttempts = 3;

        for (int attempt = 1; attempt <= maximumAttempts; attempt++) {

            System.out.print("\nEnter account number: ");
            String accountNumber = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = atmService.login(accountNumber, pin);

            if (account != null) {
                System.out.println("\nLogin successful.");
                return account;
            }

            int remainingAttempts = maximumAttempts - attempt;

            System.out.println("Incorrect account number or PIN.");
            System.out.println(
                    "Remaining attempts: " + remainingAttempts
            );
        }

        return null;
    }

    private static void showATMMenu(Account account) {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = readInteger("Select an option: ");

            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;

                case 2:
                    depositMoney(account);
                    break;

                case 3:
                    withdrawMoney(account);
                    break;

                case 4:
                    transferMoney(account);
                    break;

                case 5:
                    showMiniStatement(account);
                    break;

                case 6:
                    changePin(account);
                    break;

                case 7:
                    running = false;
                    System.out.println("\nYou have logged out successfully.");
                    break;

                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }

        System.out.println("Thank you for using the Java ATM System.");
    }

    private static void printMenu() {
        System.out.println("\n==================================");
        System.out.println("             ATM MENU");
        System.out.println("==================================");
        System.out.println("1. Balance Enquiry");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Mini Statement");
        System.out.println("6. Change PIN");
        System.out.println("7. Logout");
        System.out.println("==================================");
    }

    private static void checkBalance(Account account) {
        System.out.printf(
                "%nAvailable balance: Rs. %.2f%n",
                account.getBalance()
        );
    }

    private static void depositMoney(Account account) {
        BigDecimal amount = readAmount("Enter deposit amount: Rs. ");

        if (!isValidAmount(amount)) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        account.deposit(amount);

        System.out.printf(
                "Rs. %.2f deposited successfully.%n",
                amount
        );

        checkBalance(account);
    }

    private static void withdrawMoney(Account account) {
        BigDecimal amount = readAmount("Enter withdrawal amount: Rs. ");

        if (!isValidAmount(amount)) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }

        boolean success = account.withdraw(amount);

        if (!success) {
            System.out.println("Insufficient account balance.");
            return;
        }

        System.out.printf(
                "Rs. %.2f withdrawn successfully.%n",
                amount
        );

        checkBalance(account);
    }

    private static void transferMoney(Account sender) {
        System.out.print("Enter receiver account number: ");
        String receiverAccountNumber = scanner.nextLine().trim();

        if (!atmService.accountExists(receiverAccountNumber)) {
            System.out.println("Receiver account does not exist.");
            return;
        }

        if (sender.getAccountNumber().equals(receiverAccountNumber)) {
            System.out.println(
                    "You cannot transfer money to the same account."
            );
            return;
        }

        BigDecimal amount = readAmount("Enter transfer amount: Rs. ");

        if (!isValidAmount(amount)) {
            System.out.println("Transfer amount must be greater than zero.");
            return;
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient account balance.");
            return;
        }

        boolean success = atmService.transferMoney(
                sender,
                receiverAccountNumber,
                amount
        );

        if (success) {
            System.out.printf(
                    "Rs. %.2f transferred successfully to account %s.%n",
                    amount,
                    receiverAccountNumber
            );

            checkBalance(sender);
        } else {
            System.out.println("Money transfer failed.");
        }
    }

    private static void showMiniStatement(Account account) {
        List<Transaction> transactions =
                account.getTransactionHistory();

        System.out.println("\n================ MINI STATEMENT ================");
        System.out.println("Account number : " + account.getAccountNumber());
        System.out.println("Account holder : " + account.getAccountHolderName());
        System.out.println("------------------------------------------------");
        System.out.println(
                "DATE & TIME          | TYPE            | AMOUNT        | DESCRIPTION"
        );
        System.out.println(
                "--------------------------------------------------------------------------"
        );

        int startIndex = Math.max(0, transactions.size() - 5);

        for (int index = startIndex; index < transactions.size(); index++) {
            System.out.println(transactions.get(index));
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "Available balance: Rs. %.2f%n",
                account.getBalance()
        );
    }

    private static void changePin(Account account) {
        System.out.print("Enter current PIN: ");
        String oldPin = scanner.nextLine().trim();

        System.out.print("Enter new 4-digit PIN: ");
        String newPin = scanner.nextLine().trim();

        if (!newPin.matches("\\d{4}")) {
            System.out.println("The new PIN must contain exactly 4 digits.");
            return;
        }

        if (oldPin.equals(newPin)) {
            System.out.println(
                    "The new PIN cannot be the same as the current PIN."
            );
            return;
        }

        boolean success = account.changePin(oldPin, newPin);

        if (success) {
            System.out.println("PIN changed successfully.");
        } else {
            System.out.println("The current PIN is incorrect.");
        }
    }

    private static boolean isValidAmount(BigDecimal amount) {
        return amount != null
                && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private static BigDecimal readAmount(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        try {
            return new BigDecimal(input);
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a valid amount.");
            return null;
        }
    }

    private static int readInteger(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}