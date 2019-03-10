package ua.training;

import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Repeatable;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class ProbabilityEstimationTest {
    private final double lambda = 0.1;

    private double alpha;

    private final Function<Double, Double> F = x -> 1 - exp(-lambda * x);
    private final Function<Double, Double> f = x -> lambda * exp(-lambda * x);

    private final Function<Double, Double> G = x -> 1 - 1 / pow(1 + x, 2.0);
    private final Function<Double, Double> g = x -> 2 / pow(1 + x, 3.0);

    private final int size = 1000;

    private double[] fSample;
    private double[] gSample;

    @Before
    public void exactAlphaValue() {
        UnivariateIntegrator integrator = new TrapezoidIntegrator();

        this.alpha = integrator.integrate(1_000_000, x -> F.apply(x) * g.apply(x), 0.0, 200.0);
    }

    @Before
    public void generateSample() {
        this.fSample = new DistributionSampler(x -> - log(x) / lambda).sample(size);
        this.gSample = new DistributionSampler(x -> -1 + pow(x, -0.5)).sample(size);
    }

    @Test
    public void showAlpha() {
        System.out.println(alpha);
    }

    @Test
    public void simpleIndicatorEstimator() {
        double alphaEstimation = IntStream
                .range(0, size)
                .map(i -> fSample[i] < gSample[i] ? 1 : 0)
                .average()
                .getAsDouble();

        System.out.println(alphaEstimation);
    }

    @Test
    public void complexFDistributionEstimator() {
        double alphaEstimation = IntStream
                .range(0, size)
                .mapToDouble(i -> F.apply(gSample[i]))
                .average()
                .getAsDouble();

        System.out.println(alphaEstimation);
    }

    @Test
    public void complexGDistributionEstimator() {
        double alphaEstimation = IntStream
                .range(0, size)
                .mapToDouble(i -> 1.0 - G.apply(fSample[i]))
                .average()
                .getAsDouble();

        System.out.println(alphaEstimation);
    }

    @Test
    public void complexDensityDivisionEstimator() {
        Function<Double, Double> estimator = x -> (1 - exp(-lambda * x)) / x;
        double[] sample = new DistributionSampler(x -> -1 + 1 / (1 - sqrt(x))).sample(size);

        double alphaEstimation = IntStream
                .range(0, size)
                .mapToDouble(i -> estimator.apply(sample[i]))
                .average()
                .getAsDouble();

        System.out.println(alphaEstimation);
    }
}
