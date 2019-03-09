package ua.training.ci;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import ua.training.EmpiricalDistributionFunction;

import static java.lang.Math.sqrt;

public class NormalDistributionConfidenceInterval implements ConfidenceInterval {
    @Override
    public Interval intervalForMean(EmpiricalDistributionFunction function, double gama) {
        final double size = function.sampleSize();
        final double quantile = - (new TDistribution(size - 1.0).inverseCumulativeProbability(gama / 2.0));

        final double mean = function.sampleMean();
        final double variance = function.sampleVariance();

        final double bias = quantile * sqrt(variance) / sqrt(size);

        return new Interval(-bias + mean, bias + mean);
    }

    @Override
    public Interval intervalForVariance(EmpiricalDistributionFunction function, double gama) {
        RealDistribution distribution = new ChiSquaredDistribution(function.sampleSize() - 1);

        final double leftQuantile = distribution.inverseCumulativeProbability(gama / 2.0);
        final double rightQuantile = distribution.inverseCumulativeProbability(1  - (gama / 2.0));

        final double base = function.sampleVariance() * (function.sampleSize() - 1.0);

        return new Interval(base / rightQuantile, base / leftQuantile);
    }
}
