package ua.training.criteria;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

import static java.lang.Math.pow;

public class HomogeneityChiSquareCriteria {
    private int intervalsNumber;

    public HomogeneityChiSquareCriteria(int intervalsNumber) {
        this.intervalsNumber = intervalsNumber;
    }

    public boolean criteria(double[] xSample, double[] ySample, double alpha, double leftLimit, double rightLimit) {
        IntervalCounter[] xCounters = buildPartition(leftLimit, rightLimit);
        IntervalCounter[] yCounters = buildPartition(leftLimit, rightLimit);

        for (double point : xSample) {
            for (IntervalCounter counter : xCounters) {
                if (counter.in(point)) {
                    counter.put();
                    break;
                }
            }
        }

        for (double point : ySample) {
            for (IntervalCounter counter : yCounters) {
                if (counter.in(point)) {
                    counter.put();
                    break;
                }
            }
        }

        double delta = 0.0;

        for (int i = 0; i < xCounters.length; i++) {
            if ((xCounters[i].getCounter() + yCounters[i].getCounter()) != 0) {
                delta += (pow(xCounters[i].getCounter(), 2.0) / (xSample.length * (xCounters[i].getCounter() + yCounters[i].getCounter())));
                delta += (pow(yCounters[i].getCounter(), 2.0) / (ySample.length * (xCounters[i].getCounter() + yCounters[i].getCounter())));
            }
        }

        delta = (xSample.length + ySample.length) * (delta - 1.0);

        RealDistribution chiSquareDistribution = new ChiSquaredDistribution(intervalsNumber - 1.0);

        return chiSquareDistribution.inverseCumulativeProbability(1 - alpha) >= delta;
    }

    private IntervalCounter[] buildPartition(double leftLimit, double rightLimit) {
        IntervalCounter[] counters = new IntervalCounter[intervalsNumber];

        counters[0] = new IntervalCounter(-Double.MAX_VALUE, leftLimit);

        for (int i = 1; i < intervalsNumber - 1; i++) {
            counters[i] = new IntervalCounter(leftLimit + (i - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    leftLimit + i * (rightLimit - leftLimit) / (intervalsNumber - 2));
        }

        counters[intervalsNumber - 1] = new IntervalCounter(rightLimit, Double.MAX_VALUE);

        return counters;
    }
}
