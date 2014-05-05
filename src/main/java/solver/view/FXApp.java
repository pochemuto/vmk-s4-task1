package solver.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import solver.problem.Config;

/**
 * @author pochemuto
 */
public class FXApp extends Application {
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FXApp.class.getResource("/view.fxml"));
        Parent parent = loader.load();

        Controller controller = loader.getController();
        Scene scene = new Scene(parent, 900, 500);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.show();

        Config config = controller.getConfig();
    }
}
