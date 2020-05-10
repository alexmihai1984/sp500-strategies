package rocks.alexmihai.sp500strategies.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rocks.alexmihai.sp500strategies.model.StrategyResult;
import rocks.alexmihai.sp500strategies.service.AnalysisService;

@RestController
public class AnalysisController {

    private AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/{strategyName}")
    public StrategyResult analyze(@PathVariable("strategyName") String strategyName) {
        return this.analysisService.analyze(strategyName);
    }
}
