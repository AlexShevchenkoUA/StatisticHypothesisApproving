package ua.training;

import java.util.Arrays;
import java.util.stream.DoubleStream;

import static java.lang.Math.pow;

public class EmpiricalDistributionFunction {
    private double[] sample;

    public EmpiricalDistributionFunction(double[] sample) {
        this.sample = Arrays.copyOf(sample, sample.length);
    }

    public double cumulativeProbability(final double x) {
        return DoubleStream
                .of(sample)
                .map(point -> point < x ? 1.0 : 0.0)
                .average()
                .getAsDouble();

    }

    public double sampleMean() {
        return DoubleStream.of(sample).average().getAsDouble();
    }

    public double sampleVariance() {
        final double mean = sampleMean();

        return DoubleStream
                .of(sample)
                .map(point -> point - mean)
                .map(point -> pow(point, 2.0))
                .average()
                .getAsDouble();
    }

    public double sampleSize() {
        return sample.length;
    }
}
