package com.demkom58.nchat.client.network.processors;

import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.network.ClientPacketProcessor;
import com.demkom58.nchat.client.util.DataFX;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import org.slf4j.Logger;

import java.util.Objects;

public class AMessagePacketProcessor {
    public static void processAMessagePacket(AMessagePacket packet, ClientPacketProcessor cpp) {
        Logger logger = ClientPacketProcessor.logger;

        String msg = packet.getMessage();

        ChatController chatController = (ChatController) DataFX.Scenes.getController(ChatController.class);
        Platform.runLater(() -> {
            chatController.getMessagesView().getItems().add(msg);
            chatController.getMessagesView()
                    .lookupAll(".scroll-bar").stream()
                    .filter(br -> Objects.equals(Orientation.VERTICAL, ((ScrollBar) br).getOrientation()))
                    .findFirst().ifPresent(br -> br.visibleProperty().addListener((observable, oldValue, newValue) -> chatController.getMessagesView().scrollTo(newValue ? Integer.MAX_VALUE : 0)));
        });
        System.out.println(msg);
    }
}
