package solver.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import solver.core.Scheme;
import solver.methods.Euler;
import solver.methods.PredictorCorrector;
import solver.problem.Config;
import solver.problem.Problem;
import solver.problem.Solution;

/**
 * @author pochemuto
 */
public class Window extends JFrame {

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

                Solution solution = Problem.solve(solver, c);

                Curve curve = new Curve();
                curve.setData(solution.x, solution.y);


                plot.getCurves().clear();
                plot.addCurve(curve);
                plot.setBounds(max(solution.x), max(solution.y));
                plot.repaint();
            }
        }));
        return stack;
    }

    private double max(double[] data) {
        if (data.length == 0) {
            throw new IllegalArgumentException("empty data");
        }
        double max = data[0];
        for (double v : data) {
            if (v > max) {
                max = v;
            }
        }
        return max;
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
