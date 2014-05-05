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
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import solver.core.Scheme;
import solver.methods.PredictorCorrector;
import solver.problem.Config;
import solver.problem.Problem;
import solver.problem.Solution;

/**
 * @author pochemuto
 */
public class Controller implements Initializable {
    public LineChart<Number, Number> chart;
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

    private final ExecutorService service = Executors.newFixedThreadPool(1);

    public void reloadClick(ActionEvent event) {
        System.out.println(config.getV0());

        Task<XYChart.Series<Number, Number>> task = new Task<XYChart.Series<Number, Number>>() {

            @Override
            protected XYChart.Series<Number, Number> call() throws Exception {
                Scheme scheme = new PredictorCorrector();
                Solution solution = Problem.solve(scheme, config);

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                ObservableList<XYChart.Data<Number, Number>> data = series.getData();
                for (int i = 0; i < solution.x.length; i++) {
                    double x = solution.x[i], y = solution.y[i];

                    data.add(new XYChart.Data<>(x,y));
                }

                return series;
            }

            @Override
            protected void succeeded() {
                chart.getData().clear();
                chart.getData().add(getValue());
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
    }
}
