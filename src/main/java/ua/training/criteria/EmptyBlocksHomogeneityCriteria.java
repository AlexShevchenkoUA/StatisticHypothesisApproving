package ua.training.criteria;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Arrays;

import static java.lang.Math.pow;
import static java.util.Arrays.*;

public class EmptyBlocksHomogeneityCriteria {
    public boolean criteria(double[] xSample, double[] ySample, double alpha) {
        double[] xVariationSeries = copyOf(xSample, xSample.length);
        sort(xVariationSeries);

        double emptyBlocks = 0.0;

        if (countPoints(ySample, -Double.MAX_VALUE, xVariationSeries[0]) == 0) {
            emptyBlocks++;
        }

        if (countPoints(ySample, xVariationSeries[xVariationSeries.length - 1], Double.MAX_VALUE) == 0) {
            emptyBlocks++;
        }

        for (int i = 1; i < xVariationSeries.length; i++) {
            if (countPoints(ySample, xVariationSeries[i - 1], xVariationSeries[i]) == 0) {
                emptyBlocks++;
            }
        }

        double ro = ((double) ySample.length) / xSample.length;
        double quantile = new NormalDistribution().inverseCumulativeProbability(1 - alpha);

        double critical = xSample.length / (1 + ro) + ro * quantile * pow(xSample.length, 0.5) / pow(1 + ro, 1.5);

        return critical >= emptyBlocks;
    }

    private double countPoints(double[] array, double left, double right) {
        return stream(array).filter(point -> (left <= point) && (point < right)).count();
    }
}
