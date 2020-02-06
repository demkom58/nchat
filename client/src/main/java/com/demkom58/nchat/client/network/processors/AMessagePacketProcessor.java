package com.demkom58.nchat.client.network.processors;

import com.demkom58.divine.gui.GuiManager;
import com.demkom58.nchat.client.Client;
import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.network.ClientPacketProcessor;
import com.demkom58.nchat.common.network.handler.PacketEncoder;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import org.slf4j.Logger;

import java.util.Objects;

public class AMessagePacketProcessor {
    public static void processAMessagePacket(AMessagePacket packet, ClientPacketProcessor cpp) {
        final Logger logger = ClientPacketProcessor.LOGGER;
        final String msg = packet.getMessage().trim().replace(PacketEncoder.getFrameSymbol(), "");
        final GuiManager guiManager = Client.getClient().getGuiManager();

        ChatController chatController = Objects.requireNonNull(guiManager.getController(ChatController.class));
        Platform.runLater(() -> {
            chatController.getMessagesView().getItems().add(msg);
            chatController.getMessagesView()
                    .lookupAll(".scroll-bar").stream()
                    .filter(br -> Objects.equals(Orientation.VERTICAL, ((ScrollBar) br).getOrientation()))
                    .findFirst().ifPresent(br -> br.visibleProperty().addListener((observable, oldValue, newValue) -> chatController.getMessagesView().scrollTo(newValue ? Integer.MAX_VALUE : 0)));
        });

        logger.info(msg.replace("\n", " "));
    }
}