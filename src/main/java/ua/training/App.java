package ua.training;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import ua.training.ci.Interval;
import ua.training.ci.NormalDistributionConfidenceInterval;
import ua.training.ci.SimpleConfidenceInterval;
import ua.training.criteria.*;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        Random random = new Random();
        double[] s = new NormalDistribution().sample(1000);
        double[] w = new double[s.length];
        for (int i = 0; i < w.length; i++) {
            w[i] = s[i] * random.nextDouble();
        }
        System.out.println(new SpearmanCriteria().criteria(s, w, 0.01));
    }
}
