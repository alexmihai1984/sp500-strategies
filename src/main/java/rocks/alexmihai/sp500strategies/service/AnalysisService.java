package rocks.alexmihai.sp500strategies.service;

import rocks.alexmihai.sp500strategies.model.StrategyResult;

public interface AnalysisService {
    StrategyResult analyze(String strategyName);
}
