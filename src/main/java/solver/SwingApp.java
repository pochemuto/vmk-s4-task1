package solver;

import javax.swing.WindowConstants;

import solver.view.Window;

public class SwingApp {

    public static void main(String[] args) {
        Window w = new Window();
        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        w.setLocationRelativeTo(null);
        w.setVisible(true);
    }
}
