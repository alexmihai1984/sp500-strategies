package rocks.alexmihai.sp500strategies.service.strategies;

import org.springframework.stereotype.Service;
import rocks.alexmihai.sp500strategies.model.Sp500Value;

@Service
public class SaveAndDeployStrategy implements InvestingStrategy {
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public double investAmount(Sp500Value value) {
        return 0;
    }
}
