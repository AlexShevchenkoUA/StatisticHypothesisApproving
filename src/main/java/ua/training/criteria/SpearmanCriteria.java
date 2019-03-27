package ua.training.criteria;

import org.apache.commons.math3.distribution.NormalDistribution;
import ua.training.ci.Interval;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@SuppressWarnings("unchecked")
public class SpearmanCriteria {
    @SuppressWarnings("Duplicates")
    public boolean criteria(double[] fSample, double[] gSample, double alpha) {
        List<Integer> fRang = IntStream.range(0, fSample.length)
                .mapToObj(i -> (Pair<Integer, Double>) Pair.of(i, fSample[i]))
                .sorted(Comparator.comparingDouble(Pair::getSecond))
                .map(Pair::getFirst)
                .collect(Collectors.toList());

        List<Integer> gRang = IntStream.range(0, fSample.length)
                .mapToObj(i -> (Pair<Integer, Double>) Pair.of(i, gSample[i]))
                .sorted(Comparator.comparingDouble(Pair::getSecond))
                .map(Pair::getFirst)
                .collect(Collectors.toList());

        Integer[] rangs = new Integer[fRang.size()];

        for (int i = 0; i < fRang.size(); i++) {
            rangs[i] = gRang.indexOf(fRang.get(i));
        }

        double ro = 0.0;
        double n = fSample.length;

        for (int i = 0; i < rangs.length; i++) {
            ro += ((i - (n - 1.0) / 2.0) * (rangs[i] - (n - 1.0) / 2.0));
        }

        ro = 12.0 * ro / (n * (pow(n, 2.0) - 1));

        double critical = new NormalDistribution().inverseCumulativeProbability(1 - alpha / 2.0) / sqrt(n);

        return critical > ro;
    }

    private static class Pair<T, E> {
        private T first;
        private E second;

        private Pair(T first, E second) {
            this.first = first;
            this.second = second;
        }

        @SuppressWarnings("unchecked")
        public static <T, E> Pair of(T first, E second) {
            return new Pair(first, second);
        }

        public T getFirst() {
            return first;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public E getSecond() {
            return second;
        }

        public void setSecond(E second) {
            this.second = second;
        }
    }
}
