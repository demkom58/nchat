package com.demkom58.divine.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class GuiController {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;
    private AnchorPane pane;
    private GuiManager guiManager;

    public void init() {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

}
