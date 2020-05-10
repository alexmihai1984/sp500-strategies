package rocks.alexmihai.sp500strategies.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.alexmihai.sp500strategies.model.*;
import rocks.alexmihai.sp500strategies.service.strategies.InvestingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final static Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final Data data;
    private final Map<String, InvestingStrategy> investingStrategies;

    @Autowired
    public AnalysisServiceImpl(Data data, List<InvestingStrategy> investingStrategies) {
        this.data = data;
        this.investingStrategies = investingStrategies.stream().collect(
                Collectors.toMap(InvestingStrategy::getName, Function.identity())
        );
    }

    @Override
    public StrategyResult analyze(String strategyName) {
        if (!this.investingStrategies.containsKey(strategyName)) {
            throw  new IllegalArgumentException("No such strategy '" + strategyName + "'");
        }

        log.info("Data size: {}", data.getValues().size());

        return this.analyzeStrategy(this.investingStrategies.get(strategyName));
    }

    private StrategyResult analyzeStrategy(InvestingStrategy strategy) {
        List<Portfolio> portfolios = new ArrayList<>();

        for (var currentValue: this.data.getValues()) {
            var currentInvestment = new Investment(
                    currentValue,
                    strategy.investAmount(currentValue),
                    Investment.Type.INVESTMENT
            );

            if (currentValue.getDate().getYear() < 1998) {
                var portfolio = new Portfolio();
                portfolios.add(portfolio);
            }

            portfolios.stream()
                    .filter(Portfolio::isOpen)
                    .forEach(p -> p.add(currentInvestment));

            portfolios.stream()
                    .filter(Portfolio::isOpen)
                    .filter(p -> p.containsYears(20))
                    .forEach(p -> p.close(currentValue));
        }

        List<PortfolioResult> portfolioResults = portfolios.stream()
                .map(Portfolio::computeResult)
                .collect(Collectors.toList());

        return StrategyResult.from(portfolioResults);
    }
}
