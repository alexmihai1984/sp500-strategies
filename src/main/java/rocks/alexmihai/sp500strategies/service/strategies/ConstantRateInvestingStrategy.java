package rocks.alexmihai.sp500strategies.service.strategies;

import org.springframework.stereotype.Service;
import rocks.alexmihai.sp500strategies.model.Sp500Value;

@Service
public class ConstantRateInvestingStrategy implements InvestingStrategy {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public double investAmount(Sp500Value value) {
        return 1;
    }

    @Override
    public String toString() {
        return ConstantRateInvestingStrategy.class.toString();
    }
}
