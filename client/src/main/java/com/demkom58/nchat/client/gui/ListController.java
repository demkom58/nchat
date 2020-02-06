package com.demkom58.nchat.client.gui;

import com.demkom58.nchat.client.util.DataIP;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class ListController extends NGuiController {

    @FXML private ListView<String> ipList;
    @FXML private Label selectButton;
    @FXML private Label deleteButton;

    /**
     * Select IP button pressed.
     */
    @FXML
    public void onSelect(MouseEvent event) {
        final LoginController loginController = Objects.requireNonNull(getGuiManager().getController(LoginController.class));

        final MultipleSelectionModel<String> selectionModel = ipList.getSelectionModel();
        final ObservableList<String> list = selectionModel.getSelectedItems();

        if (list.isEmpty()) {
            getGuiManager().setGui(loginController);
            return;
        }

        loginController.getIpField().setText(selectionModel.getSelectedItems().get(0));
        getGuiManager().setGui(loginController);
    }

    /**
     * Remove IP button pressed.
     */
    @FXML
    public void onRemove(MouseEvent event) {
        ipList.getItems().remove(ipList.getSelectionModel().getSelectedItems().get(0));
        ipList.refresh();
        DataIP.saveIPList(ipList.getItems());
    }

    public ListView<String> getIpList() {
        return ipList;
    }

    public Label getSelectButton() {
        return selectButton;
    }

    public Label getDeleteButton() {
        return deleteButton;
    }

}
