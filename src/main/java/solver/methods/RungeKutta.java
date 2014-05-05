package solver.methods;

import solver.core.F;
import solver.core.Scheme;

/**
 * @author pochemuto
 */
public class RungeKutta implements Scheme {

    public double y2(F f, double t1, double y1, double t2) {
        double h2 = t2 - t1, h = h2 / 2;

        double k1 = f.eval(t1, y1),
                k2 = f.eval(t1 + h, y1 + h * k1),
                k3 = f.eval(t1 + h, y1 + h * k2),
                k4 = f.eval(t1 + h2, y1 + h2 * k3);

        return y1 + h2 / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }

}
