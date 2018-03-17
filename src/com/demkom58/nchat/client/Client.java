package com.demkom58.nchat.client;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.client.gui.*;
import com.demkom58.nchat.client.util.DataFX;
import com.demkom58.nchat.client.util.StyleLoader;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class Client extends Application {

    private static Logger logger = LoggerFactory.getLogger("[Client]");

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataFX.stage = primaryStage;
        initialize();
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("NChat");
        primaryStage.setScene(DataFX.Scenes.getScene(LoginController.class));

        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setFromValue(0);
        transition.setToValue(100);
        transition.setNode(DataFX.Scenes.getAnchorPane(LoginController.class));

        primaryStage.show();
        transition.play();
    }

    private void initialize() throws Exception {

        String stylesPath = Main.DATA_PATH + "styles/";

        File[] files = new File(stylesPath).listFiles();
        if(!StyleLoader.checkStyleVersion() && files != null) for(File file : files) file.delete();

        StyleLoader.exportFile("css/style.css", stylesPath);
        DataFX.Scenes.addScene(LoginController.class, StyleLoader.getFXMLLoaderAndExport("login.fxml", stylesPath));
        DataFX.Scenes.addScene(ListController.class, StyleLoader.getFXMLLoaderAndExport("list.fxml", stylesPath));
        DataFX.Scenes.addScene(ChatController.class, StyleLoader.getFXMLLoaderAndExport("chat.fxml", stylesPath));

        DataFX.Scenes.setAllTransparent();
        DataFX.Scenes.setAllDraggable();
    }


    public static void start(List<String> args) {
        launch((String[])args.toArray());
    }

    public static Logger getLogger() {
        return logger;
    }
}
