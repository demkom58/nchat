package com.demkom58.nchat.common.network.packets.client;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

@Data
@EqualsAndHashCode(callSuper = true)
public class CAuthPacket extends Packet<CommonPacketProcessor> {

    private final short id = 0x00;
    @NotNull private String nick = "Unknown";
    @NotNull private String protocolVersion = Main.PROTOCOL_VERSION;

    public CAuthPacket() {
        // Empty constructor for building this by methods inside.
    }

    public CAuthPacket(@NotNull String nick, @NotNull String protocolVersion) {
        this.nick = nick;
        this.protocolVersion = protocolVersion;
    }

    public CAuthPacket setNick(@NotNull String nick) {
        this.nick = nick;

        return this;
    }

    public CAuthPacket setProtocolVersion(@NotNull String protocolVersion) {
        this.protocolVersion = protocolVersion;

        return this;
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
