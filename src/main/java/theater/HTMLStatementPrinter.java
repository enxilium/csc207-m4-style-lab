package theater;

import java.util.Map;

/**
 * An HTML version of the statement printer class.
 */
public class HTMLStatementPrinter extends StatementPrinter {
    private StatementData statementData;

    public HTMLStatementPrinter(Invoice invoice, Map<String, Play> plays) {
        super(invoice, plays);
        this.statementData = new StatementData(invoice, plays);
    }

    @Override
    public String statement() {
        final StringBuilder result = new StringBuilder(String.format("<h1>Statement for %s</h1>%n",
                statementData.getCustomer()));
        result.append("<table>").append(System.lineSeparator());
        result.append(String.format(" <caption>Statement for %s</caption>%n", statementData.getCustomer()));
        result.append(" <tr><th>play</th><th>seats</th><th>cost</th></tr>").append(System.lineSeparator());
        for (PerformanceData perfData : statementData.getPerformances()) {
            // print line for this order
            result.append(String.format(" <tr><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                    perfData.getName(),
                    perfData.getAudience(),
                    formatMoney(perfData.getAmount())));
        }
        result.append("</table>").append(System.lineSeparator());

        result.append(String.format("<p>Amount owed is <em>%s</em></p>%n",
                formatMoney(statementData.getTotalAmount())));
        result.append(String.format("<p>You earned <em>%s</em> credits</p>%n", statementData.getVolumeCredits()));
        return result.toString();
    }
}
