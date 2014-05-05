package solver.view;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import solver.problem.Config;

/**
 * @author pochemuto
 */
public class ConfigForm {
    private JFormattedTextField massField;
    private JFormattedTextField koefField;
    private JFormattedTextField angleView;
    private JPanel panel;
    private JFormattedTextField speedField;
    private JFormattedTextField precisionField;


    public JPanel getPanel() {
        return panel;
    }

    public void setData(Config data) {
        massField.setValue(data.getMass());
        koefField.setValue(data.getK());
        angleView.setValue(data.getV0());
        speedField.setValue(data.getV0());
        precisionField.setValue(data.getPrecision());
    }

    public void getData(Config data) {
        data.setMass((double) massField.getValue());
        data.setK((double) koefField.getValue());
        data.setV0((double) angleView.getValue());
        data.setV0((double) speedField.getValue());
        data.setPrecision((double) precisionField.getValue());
    }

}
