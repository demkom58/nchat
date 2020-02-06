package com.demkom58.divine.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public abstract class GuiController {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;
    private AnchorPane pane;
    private GuiManager guiManager;

    public void init() { }

    public void setScene(@NotNull final Scene scene) {
        this.scene = scene;
    }

    public void setLoader(@NotNull final FXMLLoader loader) {
        this.loader = loader;
    }

    public void setStage(@NotNull final Stage stage) {
        this.stage = stage;
    }

    public void setPane(@NotNull final AnchorPane pane) {
        this.pane = pane;
    }

    public void setGuiManager(@NotNull final GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @NotNull
    public Scene getScene() {
        return scene;
    }

    @NotNull
    public Stage getStage() {
        return stage;
    }

    @NotNull
    public FXMLLoader getLoader() {
        return loader;
    }

    @NotNull
    public AnchorPane getPane() {
        return pane;
    }

    @NotNull
    public GuiManager getGuiManager() {
        return guiManager;
    }

}
