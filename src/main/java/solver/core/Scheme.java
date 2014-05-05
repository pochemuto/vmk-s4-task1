package solver.core;

/**
 * @author pochemuto
 */
public interface Scheme {
    /**
     * Вычисляет следущее значение функции y
     * решения дифференциального уравнения
     *         dy/dt = f(y,t)
     * @param f     правая часть уравнения
     * @param t1    время t1
     * @param y1    значение y в момент времени t1
     * @param t2    время в которой вычисляется значение y
     * @return значение y в момент времени t2
     */
    double y2(F f, double t1, double y1, double t2);
}
