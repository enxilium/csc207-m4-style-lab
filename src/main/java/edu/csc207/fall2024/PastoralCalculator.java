package edu.csc207.fall2024;

/**
 * Concrete implementation of calculator for postoral type plays.
 */
public class PastoralCalculator extends AbstractPerformanceCalculator {
    private Performance performance;

    public PastoralCalculator(Performance performance, Play play) {
        super(performance, play);
        this.performance = performance;
    }

    @Override
    public int amountFor() {
        int result = Constants.PASTORAL_BASE_AMOUNT;

        if (performance.getAudience() > Constants.PASTORAL_AUDIENCE_THRESHOLD) {
            result += Constants.PASTORAL_OVER_BASE_CAPACITY_PER_PERSON
                    * (performance.getAudience() - Constants.PASTORAL_AUDIENCE_THRESHOLD);
        }

        return result;
    }

    @Override
    public int volumeCreditsFor() {
        return Math.max(performance.getAudience() - Constants.PASTORAL_VOLUME_CREDIT_THRESHOLD, 0)
                + performance.getAudience() / 2;
    }
}
