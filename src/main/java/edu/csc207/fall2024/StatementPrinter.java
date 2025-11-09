package edu.csc207.fall2024;

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
                    formatMoney(performanceData.amountFor()),
                    performanceData.getAudience());
        }

        result += String.format("Amount owed is %s%n", formatMoney(totalAmount));
        result += String.format("You earned %s credits\n", volumeCredits);
        return result;
    }

    private static String formatMoney(int totalAmount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount / Constants.PERCENT_FACTOR);
    }
}
