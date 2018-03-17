package com.demkom58.nchat.common.network.packets.client;

import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.Packet;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CAuthPacket extends Packet<CommonPacketProcessor> {

    private String nick;
    private String protocolVersion;

    public String getNick() {
        return nick;
    }
    public CAuthPacket setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }
    public CAuthPacket setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    @Override
    public short getId() {
        return 0;
    }

    @Override
    public int getPayloadSize() {
        return nick.getBytes(StandardCharsets.UTF_8).length + protocolVersion.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void pack(ByteBuf buffer) {
        packString(buffer, protocolVersion);
        packString(buffer, nick);
    }

    @Override
    public void unpack(ByteBuf buffer) {
        protocolVersion = unpackString(buffer);
        nick = unpackString(buffer);
    }

    @Override
    public void processPacket(CommonPacketProcessor packetProcessor) {
        packetProcessor.processCAuthPacket(this);
    }
}
