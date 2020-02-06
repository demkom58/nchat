package com.demkom58.nchat.client.gui;

import com.demkom58.divine.gui.GuiController;
import com.demkom58.nchat.Main;
import com.demkom58.nchat.client.Client;
import com.demkom58.nchat.client.network.ClientMessenger;
import com.demkom58.nchat.client.network.User;
import com.demkom58.nchat.client.util.DataIP;
import com.demkom58.nchat.common.network.handler.PacketEncoder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginController extends NGuiController {

    @FXML private TextField ipField;
    @FXML private TextField loginField;
    @FXML private Label joinButton;
    @FXML private Label listButton;

    /**
     * IP list button pressed.
     */
    @FXML
    public void onList(MouseEvent event) {
        final ListController controller = getGuiManager().getController(ListController.class);

        if (controller == null)
            throw new NullPointerException("List controller not found!");

        final ObservableList ips = controller.getIpList().getItems();
        if(ips != null) {
            ips.clear();
            ips.addAll(DataIP.loadIPList());
        }

        getGuiManager().setGui(controller);
    }

    /**
     * Join button pressed.
     */
    @FXML
    public void onJoin(MouseEvent event){
        login();
    }

    /**
     * On Enter pressed (Login)
     */
    @FXML
    public void onEnter(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) login();
    }

    private void login() {
        String nick = loginField.getText();
        String voidName = nick.replace(" ", "");
        String fs = PacketEncoder.getFrameSymbol();

        if(nick.contains(fs))
            loginField.setText(nick.replace(fs, ""));

        if(nick.length() == 0 || nick.length() > 16 || voidName.length() == 0)
            return;

        Client.getClient().setUser(new User(nick));
        updateGuiNick(nick);

        ChatController chatController = Objects.requireNonNull(getGuiManager().getController(ChatController.class));
        chatController.getMessagesView().getItems().clear();
        chatController.getMessagesView().refresh();
        getGuiManager().setGui(chatController);

        final String fullIP = getIpField().getText().isEmpty() ? Main.STANDARD_IP : getIpField().getText();
        final int port = fullIP.split(":").length == 2 ? Integer.parseInt(fullIP.split(":")[1]) : Main.PORT;
        final String ip = fullIP.replace(":" + port, "");

        DataIP.saveIP(fullIP);

        try {
            new Thread(() -> {
                ClientMessenger.setup(ip, port);
                try {
                    ClientMessenger.getClientMessenger().run();
                } catch (Exception e) { e.printStackTrace(); }
            }).start();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Label getListButton() {
        return listButton;
    }

    public Label getJoinButton() {
        return joinButton;
    }

    public TextField getLoginField() {
        return loginField;
    }

    public TextField getIpField() {
        return ipField;
    }

    public void updateGuiNick(@NotNull final String nick) {
        final String greeting = "HELLO " + nick.toUpperCase();

        getGuiManager().getGuiMap().values().forEach(e -> {
            final GuiController controller = e.getThird();

            if (controller instanceof NGuiController)
                ((NGuiController) controller).getHelloLabel().setText(greeting);
        });
    }

}
