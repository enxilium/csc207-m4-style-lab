package theater;

/**
 * Concrete implementation of calculator for tragedy type plays.
 */
public class TragedyCalculator extends AbstractPerformanceCalculator {
    private Performance performance;

    public TragedyCalculator(Performance performance, Play play) {
        super(performance, play);
        this.performance = performance;
    }

    @Override
    public int amountFor() {
        int result = Constants.TRAGEDY_BASE_AMOUNT;

        if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
            result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                    * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
        }

        return result;
    }
}
