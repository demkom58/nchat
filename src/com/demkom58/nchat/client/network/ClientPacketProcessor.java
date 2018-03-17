package com.demkom58.nchat.client.network;

import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.network.processors.ADisconnectPacketProcessor;
import com.demkom58.nchat.client.network.processors.AMessagePacketProcessor;
import com.demkom58.nchat.client.network.processors.CAuthPacketProcessor;
import com.demkom58.nchat.client.util.DataFX;
import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ClientPacketProcessor extends CommonPacketProcessor {
    public static final Logger logger = LoggerFactory.getLogger("[CProcessor]");

    public ClientPacketProcessor(Channel channel) {
        super(channel);
    }

    @Override
    public void processCAuthPacket(CAuthPacket packet) {
        CAuthPacketProcessor.processCAuthPacket(packet, this);
    }

    @Override
    public void processAMessagePacket(AMessagePacket packet) {
        AMessagePacketProcessor.processAMessagePacket(packet, this);
    }

    @Override
    public void processADisconnectPacket(ADisconnectPacket packet) {
        ADisconnectPacketProcessor.processADisconnectPacket(packet, this);
    }
}
