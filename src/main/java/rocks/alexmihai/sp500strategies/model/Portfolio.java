package rocks.alexmihai.sp500strategies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
public class Portfolio {

    @ToString.Exclude
    @JsonIgnore
    private List<Investment> investments = new ArrayList<>();

    private boolean closed = false;
    private Sp500Value closingValue;

    public void add(Investment investment) {
        if (this.closed) {
            throw new IllegalStateException("Investment is closed");
        }

        this.investments.add(investment);

        if (investment.getValue().getDate().getMonth().equals(Month.DECEMBER)) {
            double dividend = this.totalUnits() * investment.getValue().getDividend();
            this.investments.add(new Investment(investment.getValue(), dividend, Investment.Type.DIV));
        }
    }

    private double totalUnits() {
        return this.investments.stream().mapToDouble(Investment::getUnits).sum();
    }

    public boolean isOpen() {
        return !this.closed;
    }

    public void close(Sp500Value closingValue) {
        this.closed = true;
        this.closingValue = closingValue;
    }

    public boolean containsYears(int years) {
        return years * 12 <= this.investmentsCount();
    }

    private long years() {
        return ChronoUnit.YEARS.between(
                this.investments.get(0).getValue().getDate(),
                this.investments.get(this.investments.size() - 1).getValue().getDate()
        );
    }

    public PortfolioResult computeResult() {
        double totalInvested = this.totalInvested();
        double profit = this.totalUnits() * this.closingValue.getPrice() - totalInvested;
        double totalYield = profit / totalInvested;
        double yearlyYield = Math.pow(1 + totalYield, 1.0 / this.years()) - 1;
        double averageInvestment = totalInvested / this.investmentsCount();

        return new PortfolioResult(this, totalYield, yearlyYield, averageInvestment);
    }

    private long investmentsCount() {
        return this.investments.stream()
                .filter(i -> i.getType() == Investment.Type.INVESTMENT)
                .count();
    }

    private double totalInvested() {
        return this.investments.stream()
                .filter(i -> i.getType() == Investment.Type.INVESTMENT)
                .mapToDouble(Investment::getAmount).sum();
    }

    LocalDate getStartDate() {
        return this.investments.get(0).getValue().getDate();
    }
}
