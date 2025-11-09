package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class used to transfer between calculation and display.
 */
public class StatementData {
    private final Invoice invoice;
    private final List<PerformanceData> performances = new ArrayList<>();
    private final Map<String, Play> plays;

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;

        for (Performance performance : invoice.getPerformances()) {
            performances.add(createPerformanceData(performance));
        }
    }

    private PerformanceData createPerformanceData(Performance performance) {
        final Play play = this.plays.get(performance.getPlayID());

        final AbstractPerformanceCalculator performanceCalculator =
                AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);

        return new PerformanceData(performance, play, performanceCalculator.amountFor(),
                performanceCalculator.volumeCreditsFor());
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
            result += performanceData.getAmount();
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
            result += performanceData.getVolumeCredits();
        }
        return result;
    }

    /**
     * Returns the play for the given performance.
     * @param performance the given performance
     * @return Returns the associated Play object.
     */
    public Play getPlay(Performance performance) {
        return this.plays.get(performance.getPlayID());
    }

    /**
     * Returns the amount for the given performance.
     * @param performance the given performance
     * @return Returns the amount.
     */
    public int getAmount(Performance performance) {
        final Play play = this.plays.get(performance.getPlayID());

        final AbstractPerformanceCalculator performanceCalculator =
                AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);

        return performanceCalculator.amountFor();
    }
}
