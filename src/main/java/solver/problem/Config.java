package solver.problem;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Config {
    private DoubleProperty mass = new SimpleDoubleProperty(10);
    private DoubleProperty k = new SimpleDoubleProperty(0.9);
    private DoubleProperty angle = new SimpleDoubleProperty(25);
    private DoubleProperty v0 = new SimpleDoubleProperty(45);
    private DoubleProperty precision = new SimpleDoubleProperty(0.001);

    public Config() {
    }

    public double getMass() {
        return mass.get();
    }

    public DoubleProperty massProperty() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass.set(mass);
    }

    public double getK() {
        return k.get();
    }

    public DoubleProperty kProperty() {
        return k;
    }

    public void setK(double k) {
        this.k.set(k);
    }

    public double getAngle() {
        return angle.get();
    }

    public DoubleProperty angleProperty() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle.set(angle);
    }

    public double getV0() {
        return v0.get();
    }

    public DoubleProperty v0Property() {
        return v0;
    }

    public void setV0(double v0) {
        this.v0.set(v0);
    }

    public double getPrecision() {
        return precision.get();
    }

    public DoubleProperty precisionProperty() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision.set(precision);
    }
}