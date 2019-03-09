package ua.training.ci;

import ua.training.EmpiricalDistributionFunction;

public interface ConfidenceInterval {
    Interval intervalForMean(EmpiricalDistributionFunction function, double gama);
    Interval intervalForVariance(EmpiricalDistributionFunction function, double gama);
}
