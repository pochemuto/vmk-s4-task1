package solver.view;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import solver.core.F;
import solver.core.Scheme;
import solver.methods.Euler;
import solver.methods.PredictorCorrector;
import solver.problem.Config;

/**
 * @author pochemuto
 */
public class Window extends JFrame {

    private final static double g = 9.807;

    private final Plot plot = new Plot();

    public Window() throws HeadlessException {
        setSize(900, 600);
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(10, 10));

        plot.setBorder(new EmptyBorder(10, 10, 10, 10));
        plot.setBackground(Color.WHITE);
        pane.add(plot);

        JPanel sidePanel = createSidePanel();
        sidePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.add(sidePanel, BorderLayout.WEST);
    }

    private JPanel createSidePanel() {

        JPanel stack = new JPanel();
        BoxLayout layout = new BoxLayout(stack, BoxLayout.Y_AXIS);
        stack.setLayout(layout);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel labels = new JPanel(new GridLayout(0, 1));
        labels.add(new JLabel("Масса:"));

        JPanel fields = new JPanel(new GridLayout(0, 1));
        fields.setPreferredSize(new Dimension(100, 0));

        JFormattedTextField massField = new JFormattedTextField();
        massField.setValue(10.0);
        fields.add(massField);

        panel.add(labels, BorderLayout.CENTER);
        panel.add(fields, BorderLayout.LINE_END);

        Config c = new Config();
        ConfigForm configForm = new ConfigForm();
        configForm.setData(c);

        stack.add(configForm.getPanel());
        stack.add(Box.createVerticalGlue());


        stack.add(new JButton(new AbstractAction("Обновить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scheme solver = new PredictorCorrector();
                solver = new Euler();

                configForm.getData(c);

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

                Fnum x = new Fnum(solver, fvx, t0, vx0);
                Fnum y = new Fnum(solver, fvy, t0, vy0);

                List<Double> x_values = new ArrayList<>(), y_values = new ArrayList<>();

                double t1 = t0, t2, x1 = 0, y1 = 0, x2, y2;

                double maxx = 0, maxy = 0;
                do {

                    x_values.add(x1);
                    y_values.add(y1);
                    if (x1 > maxx) {
                        maxx = x1;
                    }
                    if (y1 > maxy) {
                        maxy = y1;
                    }

                    t2 = t1 + precision;
                    x2 = solver.y2(x, t1, x1, t2);
                    y2 = solver.y2(y, t1, y1, t2);

                    t1 = t2;
                    x1 = x2;
                    y1 = y2;

                    System.out.printf("t=%4s x=%4s y=%4s\n", t1 ,x1, y1);
                } while (y2 > 0);

                Curve curve = new Curve();
                curve.setData(
                        x_values.stream().mapToDouble(Double::doubleValue).toArray(),
                        y_values.stream().mapToDouble(Double::doubleValue).toArray());

                plot.getCurves().clear();
                plot.addCurve(curve);
                plot.setBounds(maxx, maxy);
                plot.repaint();
            }
        }));
        return stack;
    }

    private static double sqr(double v) {
        return v * v;
    }

    public void addCurve(Curve c) {
        plot.addCurve(c);
    }

    public void setBounds(double x, double y) {
        plot.setBounds(x, y);
    }

    public static void main(String... args) {
        Window w = new Window();
        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Curve c = new Curve();
        int size = 1000;
        double[] x = new double[size];
        double[] y = new double[size];

        for (int i = 0; i < size; i++) {
            x[i] = ((double) i) / size;
            y[i] = Math.sin(x[i] * Math.PI);
        }
        c.setData(x, y);
        c.setColor(new Color(32, 31, 70));

        w.addCurve(c);

        w.setVisible(true);
    }
}
