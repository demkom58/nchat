package com.demkom58.nchat.client;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.gui.ListController;
import com.demkom58.nchat.client.gui.LoginController;
import com.demkom58.nchat.client.util.DataFX;
import com.demkom58.nchat.client.util.StyleLoader;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Client extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger("[Client]");

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
        File[] files = new File(Main.STYLES_PATH).listFiles();
        if(!StyleLoader.checkStyleVersion() && files != null) for(File file : files) file.delete();

        StyleLoader.exportFile("/assets/css/style.css", Main.STYLES_PATH);
        DataFX.Scenes.addScene(LoginController.class, StyleLoader.getFXMLLoaderAndExport("/assets/login.fxml", Main.STYLES_PATH));
        DataFX.Scenes.addScene(ListController.class, StyleLoader.getFXMLLoaderAndExport("/assets/list.fxml", Main.STYLES_PATH));
        DataFX.Scenes.addScene(ChatController.class, StyleLoader.getFXMLLoaderAndExport("/assets/chat.fxml", Main.STYLES_PATH));

        LoginController loginController = (LoginController) DataFX.Scenes.getController(LoginController.class);
        loginController.getIpField().setText(Main.STANDARD_IP);

        DataFX.Scenes.setAllTransparent();
        DataFX.Scenes.setAllDraggable();
    }


    public static void start(OptionSet optionSet) {
        launch();
        getLogger().info("NChat v"+Main.APP_VERSION+" is launching.");
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
