package ua.training.criteria;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.stream.Stream;

import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

public class EmptyBoxesCriteria {
    private int intervalsNumber;

    public EmptyBoxesCriteria(int intervalsNumber) {
        this.intervalsNumber = intervalsNumber;
    }

    public boolean criteria(double[] uniformDistributionSample, double alpha) {
        IntervalCounter[] counters = buildUniformPartition();

        for (double point : uniformDistributionSample) {
            for (IntervalCounter counter : counters) {
                if (counter.in(point)) {
                    counter.put();
                    break;
                }
            }
        }

        double empty = Stream.of(counters).mapToDouble(counter -> counter.getCounter() == 0.0 ? 1.0 : 0.0).sum();

        double ro = ((double) uniformDistributionSample.length) / intervalsNumber;
        double quantile = new NormalDistribution().inverseCumulativeProbability(1 - alpha);

        double criticalPoint = intervalsNumber * exp(-ro) + quantile * sqrt(intervalsNumber * exp(-ro) * (1 - exp(-ro)* (1 + ro)));

        return empty <= criticalPoint;
    }

    private IntervalCounter[] buildUniformPartition() {
        IntervalCounter[] counters = new IntervalCounter[intervalsNumber];

        for (int i = 0; i < intervalsNumber; i++) {
            counters[i] = new IntervalCounter(((double) i) / intervalsNumber, ((double) (i + 1)) / intervalsNumber);
        }

        return counters;
    }
}
