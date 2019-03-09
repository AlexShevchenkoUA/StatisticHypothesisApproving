package ua.training.ci;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import ua.training.EmpiricalDistributionFunction;

import static java.lang.Math.sqrt;

public class SimpleConfidenceInterval implements ConfidenceInterval {
    private RealDistribution distribution;

    public SimpleConfidenceInterval() {
        this.distribution = new NormalDistribution();
    }

    @Override
    public Interval intervalForMean(EmpiricalDistributionFunction function, double gama) {
        final double quantile = -distribution.inverseCumulativeProbability(gama / 2.0);
        final double mean = function.sampleMean();
        final double variance = function.sampleVariance();
        final double size = function.sampleSize();

        final double bias = quantile * sqrt(variance) / sqrt(size);

        return new Interval(-bias + mean, bias + mean);
    }

    @Override
    public Interval intervalForVariance(EmpiricalDistributionFunction function, double gama) {
        throw new UnsupportedOperationException();
    }
}
