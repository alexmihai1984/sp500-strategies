package rocks.alexmihai.sp500strategies.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioResult {
    private Portfolio portfolio;
    private double totalYield;
    private double yearlyYield;
    private double averageInvestment;
}
