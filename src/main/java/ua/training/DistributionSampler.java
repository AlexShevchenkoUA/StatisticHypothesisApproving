package ua.training;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public class DistributionSampler {
    private Random random;
    private Function<Double, Double> inverseCumulativeFunction;

    public DistributionSampler(Function<Double, Double> inverseCumulativeFunction) {
        this.random = new Random();
        this.inverseCumulativeFunction = inverseCumulativeFunction;
    }

    public double[] sample(int size) {
        return DoubleStream
                .generate(() -> inverseCumulativeFunction.apply(random.nextDouble()))
                .limit(size)
                .toArray();
    }
}
