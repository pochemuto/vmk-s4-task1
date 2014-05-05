package solver.view;

import java.awt.Color;

/**
 * @author pochemuto
 */
public class Curve {
    private double[] x;

    private double[] y;

    private String name;

    private Color color;

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public void setData(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
