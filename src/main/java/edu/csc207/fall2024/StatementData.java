package edu.csc207.fall2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class used to transfer between calculation and display.
 */
public class StatementData {
    private final Invoice invoice;
    private List<PerformanceData> performances = new ArrayList<>();
    private final Map<String, Play> plays;

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;

        for (Performance performance : invoice.getPerformances()) {
            final Play play = this.plays.get(performance.getPlayID());
            final PerformanceData performanceData = new PerformanceData(performance, play);
            performances.add(performanceData);
        }

    }

    public String getCustomer() {
        return invoice.getCustomer();
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    /**
     * Returns the total amount for this statement.
     * @return int
     */
    public int getTotalAmount() {
        int result = 0;
        for (PerformanceData performanceData : performances) {
            result += performanceData.amountFor();
        }
        return result;
    }

    /**
     * Returns the total volume credits for this statement.
     * @return int
     */
    public int getVolumeCredits() {
        int result = 0;
        for (PerformanceData performanceData : performances) {
            result += performanceData.volumeCreditsFor();
        }
        return result;
    }
}
