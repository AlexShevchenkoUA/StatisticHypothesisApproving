package ua.training.criteria;

import java.util.Objects;

public class IntervalCounter {
    private double left;
    private double right;

    private double counter;

    public IntervalCounter(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
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
        IntervalCounter that = (IntervalCounter) o;
        return Double.compare(that.left, left) == 0 &&
                Double.compare(that.right, right) == 0 &&
                Double.compare(that.counter, counter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, counter);
    }

    @Override
    public String toString() {
        return "IntervalCounter{" +
                "left=" + left +
                ", right=" + right +
                ", counter=" + counter +
                '}';
    }

    double put() {
        return ++counter;
    }

    boolean in(double x) {
        return (left <= x) && (x < right);
    }
}
