package com.demkom58.nchat.client.gui;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.client.network.ClientMessenger;
import com.demkom58.nchat.client.network.ClientUser;
import com.demkom58.nchat.client.util.DataFX;
import com.demkom58.nchat.client.util.DataIP;
import com.demkom58.nchat.common.network.handler.PacketEncoder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController extends AbstractController {

    @FXML
    private TextField ipField;
    @FXML
    private TextField loginField;
    @FXML
    private Label joinButton;
    @FXML
    private Label listButton;

    //IP list button pressed.
    @FXML
    public void onList(MouseEvent event) {
        ObservableList ips = ((ListController) DataFX.Scenes.getController(ListController.class)).getIpList().getItems();
        if(ips != null) {
            ips.clear();
            ips.addAll(DataIP.loadIPList());
        }
        DataFX.stage.setScene(DataFX.Scenes.getScene(ListController.class));
    }

    //Join button pressed.
    @FXML
    public void onJoin(MouseEvent event){
        login();
    }

    //On Enter pressed (Login)
    @FXML
    public void onEnter(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) login();
    }

    private void login() {
        String nick = loginField.getText();
        String voidName = nick.replaceAll(" ", "");
        String fs = PacketEncoder.getFrameSymbol();

        if(nick.contains(fs)) loginField.setText(nick.replaceAll(fs, ""));
        if(nick.length() == 0 || nick.length() > 16 || voidName.length() == 0) return;

        ClientUser.setName(nick);
        DataFX.updateGuiNick(nick);

        ChatController chatController = (ChatController) DataFX.Scenes.getController(ChatController.class);
        chatController.getMessagesView().getItems().clear();
        chatController.getMessagesView().refresh();
        DataFX.stage.setScene(DataFX.Scenes.getScene(ChatController.class));

        final String ip = getIpField().getText().isEmpty() ? Main.STANDART_IP : getIpField().getText();
        DataIP.saveIP(ip);
        try {
            new Thread(() -> {
                ClientMessenger.prepare();
                try {
                    ClientMessenger.getClientMessenger().run(ip, Main.PORT);
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
}
