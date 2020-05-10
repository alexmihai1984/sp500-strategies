package rocks.alexmihai.sp500strategies.service.strategies;

import org.springframework.stereotype.Service;
import rocks.alexmihai.sp500strategies.model.Sp500Value;

@Service
public class SimpleAllTimeHighBasedStrategy implements InvestingStrategy {

    private final static double MULTIPLIER = 2;

    private double ath;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public double investAmount(Sp500Value value) {
        if (value.getPrice() > this.ath) {
            this.ath = value.getPrice();
        }

        return (this.ath / value.getPrice() - 1) * MULTIPLIER + 1;
    }
}
