package edu.csc207.fall2024;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private Invoice invoice;
    private Map<String, Play> plays;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        final int volumeCredits = getVolumeCredits();
        final int totalAmount = getTotalAmount();

        String result = "Statement for " + invoice.getCustomer() + "\n";
        for (Performance performance : invoice.getPerformances()) {
            // print line for this order
            result += String.format("  %s: %s (%s seats)%n", getPlay(performance).getName(),
                    formatMoney(amountFor(performance)),
                    performance.getAudience());
        }

        result += String.format("Amount owed is %s%n", formatMoney(totalAmount));
        result += String.format("You earned %s credits\n", volumeCredits);
        return result;
    }

    private int getTotalAmount() {
        int result = 0;
        for (Performance performance : invoice.getPerformances()) {
            result += amountFor(performance);
        }
        return result;
    }

    private int getVolumeCredits() {
        int result = 0;
        for (Performance performance : invoice.getPerformances()) {
            // add volume credits
            result += volumeCreditsFor(performance);
        }
        return result;
    }

    private static String formatMoney(int totalAmount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount / Constants.PERCENT_FACTOR);
    }

    private int volumeCreditsFor(Performance performance) {
        int result = 0;

        result += Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        // add extra credit for every five comedy attendees
        if ("comedy".equals(getPlay(performance).getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }

        return result;
    }

    private Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    private int amountFor(Performance performance) {
        int result = 0;

        switch (getPlay(performance).getType()) {
            case "tragedy":
                result = Constants.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;
            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", getPlay(performance).getType()));
        }
        return result;
    }

}
