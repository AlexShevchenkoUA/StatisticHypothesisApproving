package ua.training.ci;

import java.util.Objects;

public class Interval {
    private double left;
    private double right;

    public Interval(double left, double right) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Double.compare(interval.left, left) == 0 &&
                Double.compare(interval.right, right) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
