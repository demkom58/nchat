package com.demkom58.nchat.server.network;

import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import com.demkom58.nchat.server.network.processors.ADisconnectPacketProcessor;
import com.demkom58.nchat.server.network.processors.AMessagePacketProcessor;
import com.demkom58.nchat.server.network.processors.CAuthPacketProcessor;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerPacketProcessor extends CommonPacketProcessor {

    public static final Logger LOGGER = LoggerFactory.getLogger("[SProcessor]");

    public ServerPacketProcessor(Channel channel) {
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
