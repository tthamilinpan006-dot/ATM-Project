import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private final String accountNumber;
    private final String accountHolderName;
    private String pin;
    private BigDecimal balance;

    private final List<Transaction> transactionHistory;

    public Account(
            String accountNumber,
            String accountHolderName,
            String pin,
            BigDecimal initialBalance
    ) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();

        transactionHistory.add(
                new Transaction(
                        "ACCOUNT OPENED",
                        initialBalance,
                        "Initial account balance"
                )
        );
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public boolean changePin(String oldPin, String newPin) {
        if (!validatePin(oldPin)) {
            return false;
        }

        pin = newPin;

        transactionHistory.add(
                new Transaction(
                        "PIN CHANGE",
                        BigDecimal.ZERO,
                        "PIN changed successfully"
                )
        );

        return true;
    }

    public void deposit(BigDecimal amount) {
        credit(amount, "DEPOSIT", "Cash deposited");
    }

    public boolean withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            return false;
        }

        debit(amount, "WITHDRAWAL", "Cash withdrawn");
        return true;
    }

    void credit(BigDecimal amount, String type, String description) {
        balance = balance.add(amount);

        transactionHistory.add(
                new Transaction(type, amount, description)
        );
    }

    void debit(BigDecimal amount, String type, String description) {
        balance = balance.subtract(amount);

        transactionHistory.add(
                new Transaction(type, amount, description)
        );
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
}