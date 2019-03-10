package ua.training.criteria;

import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;
import ua.training.EmpiricalDistributionFunction;

import java.util.stream.DoubleStream;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class SmirnovCriteria {
    public boolean criteria(EmpiricalDistributionFunction f, EmpiricalDistributionFunction g, double alpha) {
        double d = supremum(f, g);

        double criticalPoint = new KolmogorovSmirnovTest()
                .ksSum(sqrt(f.sampleSize() * g.sampleSize() / (f.sampleSize() + g.sampleSize())) * d, 0.0001, 10_000);

        return 1 - alpha > criticalPoint;
    }

    private double supremum(EmpiricalDistributionFunction f, EmpiricalDistributionFunction g) {
        return DoubleStream
                .concat(DoubleStream.of(f.getSample()), DoubleStream.of(g.getSample()))
                .distinct()
                .map(x -> abs(f.cumulativeProbability(x) - g.cumulativeProbability(x)))
                .max()
                .getAsDouble();
    }
}
