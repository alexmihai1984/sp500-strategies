package rocks.alexmihai.sp500strategies.service.strategies;

import org.springframework.stereotype.Service;
import rocks.alexmihai.sp500strategies.model.Sp500Value;

@Service
public class ProportionalToAllTimeHighStrategy implements InvestingStrategy {

    private final static double MULTIPLIER = 10;
    private final static double MIN_INVESTMENT = 0.1;

    private double previousAth;
    private double currentAth;
    private boolean dipped = false;


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public double investAmount(Sp500Value value) {

        this.setAllTimeHighs(value);

        double upwordsTrendFactor = 1 + (this.currentAth / this.previousAth - 1) / MULTIPLIER;

        double downwordDipFactor = 1 + (this.currentAth / value.getPrice() - 1) * MULTIPLIER;

        double amount = upwordsTrendFactor * downwordDipFactor;

        return Math.max(amount, MIN_INVESTMENT);
    }

    private void setAllTimeHighs(Sp500Value value) {
        if (this.currentAth == 0) {
            this.previousAth = value.getPrice();
            this.currentAth = value.getPrice();
        }

        if (value.getPrice() / this.currentAth < 0.7) {
            this.dipped = true;
        }

        if (value.getPrice() > this.currentAth) {
            if (this.dipped) {
                this.previousAth = this.currentAth;
                this.currentAth = value.getPrice();
                this.dipped = false;
            } else {
                this.currentAth = value.getPrice();
            }
        }
    }
}
