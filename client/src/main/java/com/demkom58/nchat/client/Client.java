package com.demkom58.nchat.client;

import com.demkom58.divine.gui.GuiManager;
import com.demkom58.divine.gui.GuiStyleLoader;
import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.gui.ListController;
import com.demkom58.nchat.client.gui.LoginController;
import com.demkom58.nchat.client.network.User;
import com.demkom58.nchat.common.Environment;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
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
    private static final String STYLES_PATH = Environment.DATA_PATH + "styles/";

    private static Client client;

    private final GuiStyleLoader styleLoader = new GuiStyleLoader();
    private GuiManager guiManager;
    private User user;

    @Override
    public void start(@NotNull final Stage stage) throws Exception {
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
        Client.client = this;

        guiManager = new GuiManager(stage);
        final File[] files = new File(STYLES_PATH).listFiles();

        if (!styleLoader.checkStyleVersion(Environment.DATA_PATH, Environment.STYLING_VERSION) && files != null)
            for (File file : files) file.delete();

        styleLoader.exportResource(Environment.class, "/assets/css/style.css", STYLES_PATH);

        final FXMLLoader loginLoader = styleLoader.getFXMLLoaderAndExport(Environment.class, "/assets/login.fxml", STYLES_PATH);
        final FXMLLoader listLoader = styleLoader.getFXMLLoaderAndExport(Environment.class, "/assets/list.fxml", STYLES_PATH);
        final FXMLLoader chatLoader = styleLoader.getFXMLLoaderAndExport(Environment.class, "/assets/chat.fxml", STYLES_PATH);

        guiManager.registerGui(LoginController.class, loginLoader, stage);
        guiManager.registerGui(ListController.class, listLoader, stage);
        guiManager.registerGui(ChatController.class, chatLoader, stage);

        LoginController loginController = Objects.requireNonNull(guiManager.getGuiPack(LoginController.class)).getThird();
        loginController.getIpField().setText(Environment.STANDARD_IP);

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
        getLogger().info("NChat v" + Environment.APP_VERSION + " is launching.");
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void setUser(@NotNull final User user) {
        this.user = user;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    public static Client getClient() {
        return client;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
