package ua.training.criteria;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

import static java.lang.Math.*;

public class ChiSquareCriteria {
    private int intervalsNumber;

    public ChiSquareCriteria(int intervalsNumber) {
        this.intervalsNumber = intervalsNumber;
    }

    public boolean criteria(double[] sample, RealDistribution distributionWithMaxLikelihoodEstimator, double paramsNumber, double alpha, double leftLimit, double rightLimit) {
        IntervalCounter[] counters = buildPartition(leftLimit, rightLimit);

        for (double point : sample) {
            for (IntervalCounter counter : counters) {
                if (counter.in(point)) {
                    counter.put();
                    break;
                }
            }
        }

        double delta = 0.0;

        for (IntervalCounter intervalCounter : counters) {
            double intervalProbability = distributionWithMaxLikelihoodEstimator.cumulativeProbability(intervalCounter.getRight()) -
                    distributionWithMaxLikelihoodEstimator.cumulativeProbability(intervalCounter.getLeft());

            delta += intervalProbability == 0 ? 0.0 : (pow(intervalCounter.getCounter() - sample.length * intervalProbability, 2.0) / (sample.length * intervalProbability));
        }

        RealDistribution chiSquareDistribution = new ChiSquaredDistribution(intervalsNumber - 1.0 - paramsNumber);

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
