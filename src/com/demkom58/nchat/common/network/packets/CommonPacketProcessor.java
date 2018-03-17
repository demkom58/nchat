package com.demkom58.nchat.common.network.packets;

import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import com.demkom58.nchat.common.network.processing.PacketProcessor;
import io.netty.channel.Channel;

public abstract class CommonPacketProcessor extends PacketProcessor {
    public CommonPacketProcessor(Channel channel) {
        super(channel);
    }

    public abstract void processCAuthPacket(CAuthPacket packet);
    public abstract void processAMessagePacket(AMessagePacket packet);
    public abstract void processADisconnectPacket(ADisconnectPacket packet);

}
