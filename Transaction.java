import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final LocalDateTime dateTime;
    private final String type;
    private final BigDecimal amount;
    private final String description;

    public Transaction(String type, BigDecimal amount, String description) {
        this.dateTime = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return String.format(
                "%-20s | %-15s | Rs. %-10.2f | %s",
                dateTime.format(formatter),
                type,
                amount,
                description
        );
    }
}