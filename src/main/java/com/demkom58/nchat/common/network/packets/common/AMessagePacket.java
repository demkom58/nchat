package com.demkom58.nchat.common.network.packets.common;

import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.Packet;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class AMessagePacket extends Packet<CommonPacketProcessor> {

    private String message;

    @Override
    public short getId() {
        return 1;
    }

    @Override
    public int getPayloadSize() {
        return getMessage().getBytes(StandardCharsets.UTF_8).length;
    }

    public AMessagePacket setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void pack(ByteBuf buffer) {
        packString(buffer, getMessage());
    }

    @Override
    public void unpack(ByteBuf buffer) {
        message = unpackString(buffer);
    }

    @Override
    public void processPacket(CommonPacketProcessor packetProcessor) {
        packetProcessor.processAMessagePacket(this);
    }
}
