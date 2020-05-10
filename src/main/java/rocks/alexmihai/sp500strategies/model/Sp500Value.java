package rocks.alexmihai.sp500strategies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Sp500Value {
    // {"Consumer Price Index": 12.46,
    // "Date": "1871-01-01",
    // "Dividend": 0.26,
    // "Earnings": 0.4,
    // "Long Interest Rate": 5.32,
    // "PE10": null,
    // "Real Dividend": 5.21,
    // "Real Earnings": 8.02,
    // "Real Price": 89.0,
    // "SP500": 4.44}

    @JsonProperty("Consumer Price Index")
    private Double consumerPriceIndex;

    @JsonProperty("Date")
    private LocalDate date;

    @JsonProperty("Dividend")
    private Double dividend;

    @JsonProperty("Earnings")
    private Double earnings;

    @JsonProperty("Long Interest Rate")
    private Double longInterestRate;

    @JsonProperty("PE10")
    private Double pe10;

    @JsonProperty("Real Dividend")
    private Double realDividend;

    @JsonProperty("Real Earnings")
    private Double realEarnings;

    @JsonProperty("Real Price")
    private Double realPrice;

    @JsonProperty("SP500")
    private Double price;
}
