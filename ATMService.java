import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ATMService {

    private final Map<String, Account> accounts;

    public ATMService() {
        accounts = new HashMap<>();
        createDemoAccounts();
    }

    private void createDemoAccounts() {
        Account accountOne = new Account(
                "1001",
                "Arun Kumar",
                "1234",
                new BigDecimal("10000.00")
        );

        Account accountTwo = new Account(
                "1002",
                "Kavya",
                "5678",
                new BigDecimal("15000.00")
        );

        accounts.put(accountOne.getAccountNumber(), accountOne);
        accounts.put(accountTwo.getAccountNumber(), accountTwo);
    }

    public Account login(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);

        if (account != null && account.validatePin(pin)) {
            return account;
        }

        return null;
    }

    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public boolean transferMoney(
            Account sender,
            String receiverAccountNumber,
            BigDecimal amount
    ) {
        Account receiver = accounts.get(receiverAccountNumber);

        if (receiver == null) {
            return false;
        }

        if (sender.getAccountNumber().equals(receiverAccountNumber)) {
            return false;
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            return false;
        }

        sender.debit(
                amount,
                "TRANSFER OUT",
                "Transferred to account " + receiverAccountNumber
        );

        receiver.credit(
                amount,
                "TRANSFER IN",
                "Received from account " + sender.getAccountNumber()
        );

        return true;
    }
}