package com.demkom58.nchat.client.gui;

import com.demkom58.divine.gui.GuiController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public abstract class NGuiController extends GuiController {

    @FXML
    private Label close;
    @FXML
    private Label helloLabel;

    //Close button pressed.
    @FXML
    public void onClose(MouseEvent event) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setNode(getGuiManager().getAnchorPane(getClass()));
        transition.setOnFinished(handler -> getGuiManager().close());
        transition.play();
    }

    public Label getClose() {
        return close;
    }

    public Label getHelloLabel() {
        return helloLabel;
    }

}
