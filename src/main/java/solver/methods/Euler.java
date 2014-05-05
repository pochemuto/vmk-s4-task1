package solver.methods;

import solver.core.F;
import solver.core.Scheme;

/**
 * @author pochemuto
 */
public class Euler implements Scheme {

    @Override
    public double y2(F f, double t1, double y1, double t2) {
        return y1 + (t2 - t1) * f.eval(t1, y1);
    }
}
