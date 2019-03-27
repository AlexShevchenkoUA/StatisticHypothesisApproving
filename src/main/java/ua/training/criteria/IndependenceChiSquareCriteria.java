package ua.training.criteria;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

import static java.lang.Math.pow;

public class IndependenceChiSquareCriteria {
    private int intervalsNumber;

    public IndependenceChiSquareCriteria(int intervalsNumber) {
        this.intervalsNumber = intervalsNumber;
    }

    public boolean criteria(double[] fSample, double[] gSample, double alpha, double leftLimit, double rightLimit) {
        TwoDimensionalIntervalCounter[][] counters = buildPartition(leftLimit, rightLimit);

        for (int i = 0; i < fSample.length; i++) {
            for (int j = 0; j < intervalsNumber; j++) {
                for (int k = 0; k < intervalsNumber; k++) {
                    if (counters[j][k].in(fSample[i], gSample[i])) {
                        counters[j][k].put();
                    }
                }
            }
        }

        double[] fCollector = new double[intervalsNumber];
        double[] gCollector = new double[intervalsNumber];

        for (int i = 0; i < intervalsNumber; i++) {
            for (int j = 0; j < intervalsNumber; j++) {
                fCollector[i] += counters[i][j].getCounter();
                gCollector[i] += counters[j][i].getCounter();
            }
        }

        double delta = 0.0;

        for (int i = 0; i < intervalsNumber; i++) {
            for (int j = 0; j < intervalsNumber; j++) {
                if (!(fCollector[i] == 0 || gCollector[j] == 0)) {
                    delta += (pow(counters[i][j].getCounter(), 2.0) / (fCollector[i] * gCollector[j]));
                }
            }
        }

        delta = fSample.length * (delta - 1);

        RealDistribution chiSquareDistribution = new ChiSquaredDistribution(pow(intervalsNumber - 1.0, 2.0));

        return chiSquareDistribution.inverseCumulativeProbability(1 - alpha) >= delta;
    }

    private TwoDimensionalIntervalCounter[][] buildPartition(double leftLimit, double rightLimit) {
        TwoDimensionalIntervalCounter[][] counters = new TwoDimensionalIntervalCounter[intervalsNumber][intervalsNumber];

        counters[0][0] = new TwoDimensionalIntervalCounter(-Double.MAX_VALUE, leftLimit, -Double.MAX_VALUE, leftLimit);
        counters[intervalsNumber - 1][0] = new TwoDimensionalIntervalCounter(rightLimit, Double.MAX_VALUE, -Double.MAX_VALUE, leftLimit);
        counters[0][intervalsNumber - 1] = new TwoDimensionalIntervalCounter(-Double.MAX_VALUE, leftLimit, rightLimit, Double.MAX_VALUE);
        counters[intervalsNumber - 1][intervalsNumber - 1] = new TwoDimensionalIntervalCounter(rightLimit, Double.MAX_VALUE, rightLimit, Double.MAX_VALUE);

        for (int i = 1; i < intervalsNumber - 1; i++) {
            counters[0][i] = new TwoDimensionalIntervalCounter(
                    -Double.MAX_VALUE,
                    leftLimit,
                    leftLimit + (i - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    leftLimit + i * (rightLimit - leftLimit) / (intervalsNumber - 2)
            );

            counters[intervalsNumber - 1][i] = new TwoDimensionalIntervalCounter(
                    rightLimit,
                    Double.MAX_VALUE,
                    leftLimit + (i - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    leftLimit + i * (rightLimit - leftLimit) / (intervalsNumber - 2)
            );

            counters[i][0] = counters[0][i] = new TwoDimensionalIntervalCounter(
                    leftLimit + (i - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    leftLimit + i * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    -Double.MAX_VALUE,
                    leftLimit
            );

            counters[i][intervalsNumber - 1] = new TwoDimensionalIntervalCounter(
                    leftLimit + (i - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    leftLimit + i * (rightLimit - leftLimit) / (intervalsNumber - 2),
                    rightLimit,
                    Double.MAX_VALUE
            );
        }

        for (int i = 0; i < intervalsNumber - 1; i++) {
            for (int j = 0; j < intervalsNumber - 1; j++) {
                counters[i][j] = new TwoDimensionalIntervalCounter(
                        leftLimit + (i - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                        leftLimit + i * (rightLimit - leftLimit) / (intervalsNumber - 2),
                        leftLimit + (j - 1) * (rightLimit - leftLimit) / (intervalsNumber - 2),
                        leftLimit + j * (rightLimit - leftLimit) / (intervalsNumber - 2)
                );
            }
        }

        return counters;
    }
}
