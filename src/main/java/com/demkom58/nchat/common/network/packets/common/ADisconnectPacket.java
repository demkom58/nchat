package com.demkom58.nchat.common.network.packets.common;

import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.Packet;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class ADisconnectPacket extends Packet<CommonPacketProcessor> {

    private String reason;

    public String getReason() {
        return reason;
    }
    public ADisconnectPacket setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public short getId() {
        return 2;
    }

    @Override
    public int getPayloadSize() {
        return getReason().getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void pack(ByteBuf buffer) {
        packString(buffer, getReason());
    }

    @Override
    public void unpack(ByteBuf buffer) {
        reason = unpackString(buffer);
    }

    @Override
    public void processPacket(CommonPacketProcessor packetProcessor) {
        packetProcessor.processADisconnectPacket(this);
    }
}
