package rocks.alexmihai.sp500strategies.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StrategyResult {

    private PortfolioResult bestYielding;
    private PortfolioResult worstYielding;
    private double averageYield;
    private int numberOfNegativeYieldingResults;

    public static StrategyResult from(List<PortfolioResult> portfolioResults) {
        PortfolioResult bestYielding = null;
        PortfolioResult worstYielding = null;
        double yieldSum = 0;
        double count = 0;
        for (PortfolioResult portfolioResult : portfolioResults) {
            if (portfolioResult.getPortfolio().getStartDate().getYear() < 1900) {
                continue;
            }

            if (bestYielding == null || bestYielding.getYearlyYield() < portfolioResult.getYearlyYield()) {
                bestYielding = portfolioResult;
            }

            if (worstYielding == null || worstYielding.getYearlyYield() > portfolioResult.getYearlyYield()) {
                worstYielding = portfolioResult;
            }

            yieldSum += portfolioResult.getYearlyYield();
            count++;
        }

        double averageYield = yieldSum / count;

        return new StrategyResult(bestYielding, worstYielding, averageYield, -1);
    }
}
