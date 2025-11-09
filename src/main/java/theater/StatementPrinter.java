package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private final StatementData statementData;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.statementData = new StatementData(invoice, plays);
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        return renderPlainText();
    }

    private String renderPlainText() {
        final int volumeCredits = statementData.getVolumeCredits();
        final int totalAmount = statementData.getTotalAmount();

        String result = "Statement for " + statementData.getCustomer() + "\n";
        for (PerformanceData performanceData : statementData.getPerformances()) {
            // print line for this order
            result += String.format("  %s: %s (%s seats)%n", performanceData.getName(),
                    usd(performanceData.getAmount()),
                    performanceData.getAudience());
        }

        result += String.format("Amount owed is %s%n", usd(totalAmount));
        result += String.format("You earned %s credits\n", volumeCredits);
        return result;
    }

    /**
     * Formats the given amount as a monetary string.
     * @param totalAmount The amount to be formatted
     * @return String representing the monetary value
     */
    public static String usd(int totalAmount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount / Constants.PERCENT_FACTOR);
    }

    /**
     * Returns the total amount for this statement.
     * @return int
     */
    public int getTotalAmount() {
        return statementData.getTotalAmount();
    }

    /**
     * Returns the total volume credits for this statement.
     * @return int
     */
    public int getVolumeCredits() {
        return statementData.getVolumeCredits();
    }

    /**
     * Returns the play for the given performance.
     * @param performance the given performance
     * @return Returns the associated Play object.
     */
    public Play getPlay(Performance performance) {
        return statementData.getPlay(performance);
    }
}
