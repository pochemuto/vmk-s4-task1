package solver.problem;

import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;

import solver.core.F;
import solver.core.Scheme;
import solver.view.Fnum;

/**
 * @author pochemuto
 */
public class Problem {
    private final static double g = 9.807;

    public static Solution solve(Scheme scheme, Config c) {

        final double v0 = c.getV0();
        final double k = c.getK();
        final double m = c.getMass();
        final double alpha = Math.toRadians(c.getAngle());
        final double t0 = 0;
        final double mu = k / m;
        final double precision = c.getPrecision();

        F fvx = (t, vx) -> -2 * mu * v0 * sin(alpha) / g * vx;
        F fvy = (t, vy) -> -2 * mu * v0 * sin(alpha) / g * vy - 2 * sin(alpha);

        final double vx0 = cos(alpha);
        final double vy0 = sin(alpha);

        Fnum x = new Fnum(scheme, fvx, t0, vx0);
        Fnum y = new Fnum(scheme, fvy, t0, vy0);

        List<Double> x_values = new ArrayList<>(), y_values = new ArrayList<>();


        double toPhys = 2 * v0 * v0 * sin(alpha) / g;

        double t1 = t0, t2, x1 = 0, y1 = 0, x2, y2;
        x_values.add(x1);
        y_values.add(y1);

        do {
            t2 = t1 + precision;
            x2 = scheme.y2(x, t1, x1, t2);
            y2 = scheme.y2(y, t1, y1, t2);

            t1 = t2;
            x1 = x2;
            y1 = y2;

            x_values.add(x1 * toPhys);
            y_values.add(y1 * toPhys);

        } while (y2 > 0);

        return createSolution(x_values, y_values);

    }

    public static Solution analytic(Config c) {
        final double v0 = c.getV0();
        final double k = c.getK();
        final double m = c.getMass();
        final double alpha = Math.toRadians(c.getAngle());
        final double t0 = 0;
        final double mu = k / m;
        final double precision = c.getPrecision();

        List<Double> x_values = new ArrayList<>(), y_values = new ArrayList<>();
        double x, y, t = 0;

        do {
            x = v0 * cos(alpha) / mu * (1 - exp(-mu * t));
            y = (g / mu   + v0 * sin(alpha)) * (1 - exp(-mu * t)) / mu - g * t / mu;
            t += precision; //TODO precision;

            x_values.add(x);
            y_values.add(y);
        } while (y >= 0);

        return createSolution(x_values, y_values);
    }

    private static Solution createSolution(List<Double> x_values, List<Double> y_values) {
        return new Solution(x_values.stream().mapToDouble(Double::doubleValue).toArray(),
                y_values.stream().mapToDouble(Double::doubleValue).toArray());
    }
}
