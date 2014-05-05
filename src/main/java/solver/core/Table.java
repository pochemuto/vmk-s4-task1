package solver.core;


/**
 * @author pochemuto
 */
public class Table  {
    private final double x0;
    private final double range;
    private final double[] y;
    private final double step;

    public Table(double x0, double xn, double[] y) {
        this.x0 = x0;
        range = xn - x0;
        this.y = y;
        step = range / (y.length - 1);
    }

    public double evaluate(double x) {
        int n = (int) (x / step);
        if (n < 0 || n >= y.length) throw new IllegalArgumentException("out of range");
        return y[n];
    }
}
