package theater;

/**
 * Concrete implementation of calculator for history type plays.
 */
public class HistoryCalculator extends AbstractPerformanceCalculator {
    private Performance performance;

    public HistoryCalculator(Performance performance, Play play) {
        super(performance, play);
        this.performance = performance;
    }

    @Override
    public int amountFor() {
        int result = Constants.HISTORY_BASE_AMOUNT;
        if (performance.getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
            result += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON
                    * (performance.getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    @Override
    public int volumeCreditsFor() {
        int result = 0;
        result += Math.max(performance.getAudience() - Constants.HISTORY_VOLUME_CREDIT_THRESHOLD, 0);
        return result;
    }
}
