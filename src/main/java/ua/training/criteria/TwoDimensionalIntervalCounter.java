package ua.training.criteria;

import java.util.Objects;

public class TwoDimensionalIntervalCounter {
    private double xLeft;
    private double xRight;

    private double yLeft;
    private double yRight;

    private double counter;

    public TwoDimensionalIntervalCounter(double xLeft, double xRight, double yLeft, double yRight) {
        this.xLeft = xLeft;
        this.xRight = xRight;
        this.yLeft = yLeft;
        this.yRight = yRight;
    }

    public double getCounter() {
        return counter;
    }

    public void setCounter(double counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoDimensionalIntervalCounter that = (TwoDimensionalIntervalCounter) o;
        return Double.compare(that.xLeft, xLeft) == 0 &&
                Double.compare(that.xRight, xRight) == 0 &&
                Double.compare(that.yLeft, yLeft) == 0 &&
                Double.compare(that.yRight, yRight) == 0 &&
                Double.compare(that.counter, counter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xLeft, xRight, yLeft, yRight, counter);
    }

    double put() {
        return ++counter;
    }

    boolean in(double x, double y) {
        return (xLeft <= x) && (x < xRight) && (yLeft <= y) && (y < yRight);
    }
}
