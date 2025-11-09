package theater;

/**
 * Abstract class for calculating performance metrics.
 */
abstract class AbstractPerformanceCalculator {
    private Performance performance;
    private Play play;

    AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    static AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            case "history":
                return new HistoryCalculator(performance, play);
            case "pastoral":
                return new PastoralCalculator(performance, play);
            default:
                throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        }
    }

    /**
     * Returns the amount for the given performance.
     * @return int
     * @throws RuntimeException If the type is not found.
     */
    abstract int amountFor();

    /**
     * Returns the volume credits for the given performance.
     * @return int
     */
    public int volumeCreditsFor() {
        int result = 0;
        result += Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        return result;
    }
}
