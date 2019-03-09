package ua.training;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import ua.training.ci.ConfidenceInterval;
import ua.training.ci.NormalDistributionConfidenceInterval;
import ua.training.ci.SimpleConfidenceInterval;

public class App {
    public static void main(String[] args) {
        RealDistribution distribution = new NormalDistribution();
        ConfidenceInterval confidenceInterval = new SimpleConfidenceInterval();
        ConfidenceInterval normalInterval = new NormalDistributionConfidenceInterval();

        for (int i = 1; i < 20; i++) {
            EmpiricalDistributionFunction function = new EmpiricalDistributionFunction(distribution.sample(1 << i));

            System.out.println(normalInterval.intervalForVariance(function, 0.01));
        }
    }
}
