package solver.problem;

import static java.lang.Math.cos;
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
        final double alpha = Math.toRadians(c.getV0());
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

        double t1 = t0, t2, x1 = 0, y1 = 0, x2, y2;

        do {
            x_values.add(x1);
            y_values.add(y1);

            t2 = t1 + precision;
            x2 = scheme.y2(x, t1, x1, t2);
            y2 = scheme.y2(y, t1, y1, t2);

            t1 = t2;
            x1 = x2;
            y1 = y2;

        } while (y2 > 0);

        return new Solution(x_values.stream().mapToDouble(Double::doubleValue).toArray(),
                y_values.stream().mapToDouble(Double::doubleValue).toArray());

    }
}
