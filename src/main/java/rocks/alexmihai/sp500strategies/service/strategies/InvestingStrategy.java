package rocks.alexmihai.sp500strategies.service.strategies;

import rocks.alexmihai.sp500strategies.model.Sp500Value;

public interface InvestingStrategy {

    String getName();

    double investAmount(Sp500Value value);
}
