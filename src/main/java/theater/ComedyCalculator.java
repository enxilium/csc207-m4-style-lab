package theater;

/**
 * Concrete implementation of calculator for comedy type plays.
 */
public class ComedyCalculator extends AbstractPerformanceCalculator {
    private Performance performance;

    public ComedyCalculator(Performance performance, Play play) {
        super(performance, play);
        this.performance = performance;
    }

    @Override
    public int amountFor() {
        int result = Constants.COMEDY_BASE_AMOUNT;

        if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
            result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                    + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                    * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
        }

        result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();

        return result;
    }

    @Override
    public int volumeCreditsFor() {
        int result = 0;

        result += Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;

        return result;
    }
}
