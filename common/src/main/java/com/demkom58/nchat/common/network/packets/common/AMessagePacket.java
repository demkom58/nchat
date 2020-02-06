package com.demkom58.nchat.common.network.packets.common;

import com.demkom58.nchat.common.network.packets.CommonPacketProcessor;
import com.demkom58.nchat.common.network.packets.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

@Data
@EqualsAndHashCode(callSuper = true)
public class AMessagePacket extends Packet<CommonPacketProcessor> {

    private final short id = 1;
    @NotNull private String message = "";

    public AMessagePacket() {}

    public AMessagePacket(@NotNull String message) {
        this.message = message;
    }

    public AMessagePacket setMessage(@NotNull String message) {
        this.message = message;
        return this;
    }

    @Override
    public int getPayloadSize() {
        return getMessage().getBytes(StandardCharsets.UTF_8).length;
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
