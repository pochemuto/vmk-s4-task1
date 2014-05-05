package solver.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.converter.NumberStringConverter;
import solver.core.Scheme;
import solver.problem.Config;
import solver.problem.Problem;
import solver.problem.Solution;

/**
 * @author pochemuto
 */
public class Controller implements Initializable {
    public LineChart<Number, Number> chart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;
    public ToggleGroup schemeGroup;
    public CheckBox points;
    @FXML
    private TextField speedField;
    @FXML
    private TextField massField;
    @FXML
    private TextField kField;
    @FXML
    private TextField angleField;
    @FXML
    private TextField precisionField;

    private Config config = new Config();

    private final ExecutorService service = Executors.newFixedThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("Solving Thread");
        return thread;
    });

    public void addClick(ActionEvent event) {
        RadioButton toggle = (RadioButton) schemeGroup.getSelectedToggle();
        final Scheme scheme = (Scheme) toggle.getUserData();
        final String schemeName = toggle.getText();

        Task<XYChart.Series<Number, Number>> task = new Task<XYChart.Series<Number, Number>>() {
            private double maxX;
            private double maxY;

            @Override
            protected XYChart.Series<Number, Number> call() throws Exception {
                Solution solution;
                if (scheme != null) {
                    solution = Problem.solve(scheme, config);
                } else {
                    solution = Problem.analytic(config);
                }

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                ObservableList<XYChart.Data<Number, Number>> data = series.getData();
                for (int i = 0; i < solution.x.length; i++) {
                    double x = solution.x[i], y = solution.y[i];
                    if (x>maxX) maxX = x;
                    if (y>maxY) maxY = y;

                    data.add(new XYChart.Data<>(x,y));
                }
                series.setName(schemeName);

                return series;
            }

            @Override
            protected void succeeded() {
                double maxBound = Math.max(maxX, maxY);


                chart.getData().add(getValue());
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(maxBound);
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(maxBound);
            }
        };

        service.submit(task);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        speedField.textProperty().bindBidirectional(config.v0Property(), new NumberStringConverter());
        massField.textProperty().bindBidirectional(config.massProperty(), new NumberStringConverter());
        angleField.textProperty().bindBidirectional(config.angleProperty(), new NumberStringConverter());
        kField.textProperty().bindBidirectional(config.kProperty(), new NumberStringConverter());
        precisionField.textProperty().bindBidirectional(config.precisionProperty(), new NumberStringConverter());
        chart.createSymbolsProperty().bind(points.selectedProperty());
    }

    public void clearClick(ActionEvent event) {
        chart.getData().clear();
    }
}
