package solver.view;

import solver.core.F;
import solver.core.Scheme;

/**
 * Реализация функции F, значение которой вычисляется численным методом
 * по заданной схеме
 *
 * @author pochemuto
 */
public class Fnum implements F {
    private final Scheme scheme;
    private final F f;
    private double t1;
    private double f1;

    public Fnum(Scheme scheme, F f, double t0, double f0) {
        this.scheme = scheme;
        this.f = f;
        this.t1 = t0;
        this.f1 = f0;
    }

    @Override
    public double eval(double t, double x) {
        assert t >= t1;

        double f2 = eval(t, x, scheme.y2(f, t1, f1, t));
        this.t1 = t;
        this.f1 = f2;
        return f2;
    }

    /**
     * Может быть переопределен, если правая часть состоит не только из выражения, вычисленного по схеме,
     * а например F(t,x) = v(t,x)*x, где v(t,x) вычисляется приближенно
     * @return
     */
    protected double eval(double t, double x, double f) {
        return f;
    }

}
