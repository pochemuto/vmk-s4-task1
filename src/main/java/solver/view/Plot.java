package solver.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pochemuto
 */
public class Plot extends JPanel {
    private double rangeX = 1;
    private double rangeY = 1;

    private final List<Curve> curves = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics graphics) {
        Insets insets = getInsets();

        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = buffer.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.translate(insets.left, insets.top);

        int width = getWidth() - insets.left - insets.right, height = getHeight() - insets.top - insets.bottom;
        for (Curve curve : curves) {
            paintCurve(width, height, g, curve);
        }
        g.translate(0,0);

        graphics.drawImage(buffer, 0,0, null);
    }

    private void paintCurve(int width, int height, Graphics2D g, Curve curve) {
        Color color = curve.getColor();
        g.setColor(color == null ? selectColor() : color);

        final int size = 2;
        double[] x = curve.getX();
        double[] y = curve.getY();
        int count;
        if (x.length != y.length) {
            LOGGER.debug("разное количество координат по x и по y");
            count = Math.min(x.length, y.length);
        } else {
            count = x.length;
        }

        double scaleX = width / rangeX, scaleY = height / rangeY;
        double scale = Math.min(scaleX, scaleY);
        for (int i = 0; i < count; i++) {
            g.fillOval(coordinate(x[i], size, scale), height - coordinate(y[i], size, scale), size, size);
        }
    }

    private int coordinate(double x, int size, double scale) {
        return (int) (x * scale - size / 2);
    }

    private Color selectColor() {
        return Color.BLACK;
    }

    public List<Curve> getCurves() {
        return curves;
    }

    public void addCurve(Curve c) {
        curves.add(c);
    }

    public void removeCurve(Curve c) {
        curves.remove(c);
    }

    public void setBounds(double x, double y) {
        rangeX = x;
        rangeY = y;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(Plot.class);
}
