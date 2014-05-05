package solver.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import solver.problem.Config;

/**
 * @author pochemuto
 */
public class Controller {
    public ObjectProperty<Config> config = new SimpleObjectProperty<>(new Config());

    public Config getConfig() {
        return config.get();
    }

    public ObjectProperty<Config> configProperty() {
        return config;
    }

    public void setConfig(Config config) {
        this.config.set(config);
    }
}
