package solver.methods;

import solver.core.F;
import solver.core.Scheme;

/**
 * @author pochemuto
 */
public class PredictorCorrector implements Scheme {

    public double y2(F f, double t1, double y1, double t2) {
        double deltaT = t2 - t1;
        double f1 = f.eval(t1, y1);
        double y_predictor = y1 + deltaT * f1;
        return y1 + deltaT / 2 * (f1 + f.eval(t2, y_predictor));
    }

}
