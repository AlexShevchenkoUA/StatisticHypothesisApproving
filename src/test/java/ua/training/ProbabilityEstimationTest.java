package ua.training;

import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.Math.*;
import static java.lang.String.format;

public class ProbabilityEstimationTest {
    private final double lambda = 0.1;

    private final double error = 0.01;

    private double alpha;

    private final DistributionSampler fDistribution = new DistributionSampler(x -> - log(x) / lambda);
    private final DistributionSampler gDistribution = new DistributionSampler(x -> -1 + pow(x, -0.33));

    private final Function<Double, Double> inverseCpf = x -> new LaguerreSolver().solve(50, new PolynomialFunction(new double[] {-x, -3.0 * x, 1.0 - x, 3 * (1.0 - x)}), 0.0, 200.0);
    private final DistributionSampler gamaDistribution = new DistributionSampler(inverseCpf);
    private final Function<Double, Double> gamaEstimator = x -> (1 - exp(-lambda * x)) / (2.0 * x);

    private final Function<Double, Double> F = x -> 1 - exp(-lambda * x);
    private final Function<Double, Double> f = x -> lambda * exp(-lambda * x);

    private final Function<Double, Double> G = x -> 1 - 1 / pow(1 + x, 3.0);
    private final Function<Double, Double> g = x -> 3 / pow(1 + x, 4.0);

    private final double z = 2.575;

    @Before
    public void exactAlphaValue() {
        UnivariateIntegrator integrator = new TrapezoidIntegrator();

        this.alpha = integrator.integrate(1_000_000, x -> F.apply(x) * g.apply(x), 0.0, 200.0);
    }

    @Test
    public void showAlpha() {
        System.out.println(alpha);
    }

    public void templateTest(int init, Supplier<Double> eventGenerator) {
        int size =  init;

        double critical;

        double mean;
        double variance;

        double sum = 0.0;
        double squared_sum = 0.0;

        while (true) {
            double event = eventGenerator.get();

            sum += event;
            squared_sum += pow(event, 2.0);

            size++;

            mean = sum / size;
            variance = squared_sum / size - pow(mean, 2.0);

            critical = Math.pow(z / (error * mean), 2.0) * variance;

            if (size > critical) {
                break;
            }
        }

        System.out.println(format("Size = %d Estimator = %f", size, mean));
    }

    @Test
    public void simpleIndicatorEstimator() {
        System.out.println("Indicator estimator:");
        templateTest(20000, () -> fDistribution.next() < gDistribution.next() ? 1.0 : 0.0);
    }

    @Test
    public void complexFDistributionEstimator() {
        System.out.println("F Estimator:");
        templateTest(5000, () -> F.apply(gDistribution.next()));
    }

    @Test
    public void complexGDistributionEstimator() {
        System.out.println("G Estimator");
        templateTest(20000, () -> 1.0 - G.apply(fDistribution.next()));
    }

    @Test
    public void complexDensityDivisionEstimator() {
        System.out.println("Density Division Estimator");
        templateTest(100, () -> gamaEstimator.apply(gamaDistribution.next()));
    }
}
