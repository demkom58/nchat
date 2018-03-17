package com.demkom58.nchat.client.gui;

import com.demkom58.nchat.client.util.DataFX;
import com.demkom58.nchat.client.util.DataIP;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ListController extends AbstractController {

    @FXML
    private ListView ipList;
    @FXML
    private Label selectButton;
    @FXML
    private Label deleteButton;

    //Select IP button pressed.
    @FXML
    public void onSelect(MouseEvent event) {
        LoginController loginController = (LoginController) DataFX.Scenes.getController(LoginController.class);
        TextField ipField = loginController.getIpField();

        ObservableList list = getIpList().getSelectionModel().getSelectedItems();
        if(list.isEmpty()) {
            DataFX.stage.setScene(loginController.getScene());
            return;
        }

        ipField.setText((String)getIpList().getSelectionModel().getSelectedItems().get(0));
        DataFX.stage.setScene(loginController.getScene());
    }
    //Remove IP button pressed.
    @FXML
    public void onRemove(MouseEvent event) {
        getIpList().getItems().remove(getIpList().getSelectionModel().getSelectedItems().get(0));
        getIpList().refresh();
        DataIP.saveIPList(getIpList().getItems());
    }

    public ListView getIpList() {
        return ipList;
    }
    public Label getSelectButton() {
        return selectButton;
    }
    public Label getDeleteButton() {
        return deleteButton;
    }
}
