package com.demkom58.nchat.client;

import com.demkom58.divine.gui.GuiManager;
import com.demkom58.divine.gui.GuiStyleLoader;
import com.demkom58.nchat.Main;
import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.gui.ListController;
import com.demkom58.nchat.client.gui.LoginController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import joptsimple.OptionSet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class Client extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger("[Client]");
    private static Client client;

    private GuiStyleLoader styleLoader = new GuiStyleLoader();
    private GuiManager guiManager;

    @Override
    public void start(@NotNull final Stage stage) throws Exception {
        Client.client = this;

        guiManager = new GuiManager(stage);
        final File[] files = new File(Main.STYLES_PATH).listFiles();

        if(!styleLoader.checkStyleVersion(Main.DATA_PATH, Main.STYLING_VERSION) && files != null)
            for(File file : files) file.delete();

        styleLoader.exportResource(Main.class, "/assets/css/style.css", Main.STYLES_PATH);

        final FXMLLoader loginLoader = styleLoader.getFXMLLoaderAndExport(Main.class, "/assets/login.fxml", Main.STYLES_PATH);
        final FXMLLoader listLoader = styleLoader.getFXMLLoaderAndExport(Main.class, "/assets/list.fxml", Main.STYLES_PATH);
        final FXMLLoader chatLoader = styleLoader.getFXMLLoaderAndExport(Main.class, "/assets/chat.fxml", Main.STYLES_PATH);

        guiManager.registerGui(LoginController.class, loginLoader, stage);
        guiManager.registerGui(ListController.class, listLoader, stage);
        guiManager.registerGui(ChatController.class, chatLoader, stage);

        LoginController loginController = Objects.requireNonNull(guiManager.getGuiPack(LoginController.class)).getThird();
        loginController.getIpField().setText(Main.STANDARD_IP);

        guiManager.setAllTransparent();
        guiManager.setAllDraggable();

        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("NChat");

        guiManager.setGui(LoginController.class);

        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setFromValue(0);
        transition.setToValue(100);
        transition.setNode(loginController.getPane());

        stage.show();
        transition.play();
    }


    public static void start(@NotNull final OptionSet optionSet) {
        launch();
        getLogger().info("NChat v"+Main.APP_VERSION+" is launching.");
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public static Client getClient() {
        return client;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
