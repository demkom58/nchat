package com.demkom58.nchat.client.gui;

import com.demkom58.nchat.client.util.DataFX;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public abstract class AbstractController {

    @FXML
    private Label close;
    @FXML
    private Label helloLabel;
    @FXML
    private Scene scene;

    //Close button pressed.
    @FXML
    public void onClose(MouseEvent event) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setNode(DataFX.Scenes.getAnchorPane(getClass()));
        transition.setOnFinished(handler -> DataFX.close());
        transition.play();
    }

    public Scene getScene() {
        return scene;
    }
    public Label getClose() {
        return close;
    }
    public Label getHelloLabel() {
        return helloLabel;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
