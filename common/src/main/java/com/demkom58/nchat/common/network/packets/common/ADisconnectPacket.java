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
public class ADisconnectPacket extends Packet<CommonPacketProcessor> {

    private final short id = 2;
    @NotNull private String reason = "No reason.";

    public ADisconnectPacket() {}

    public ADisconnectPacket(@NotNull String reason) {
        this.reason = reason;
    }

    public ADisconnectPacket setReason(@NotNull String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public int getPayloadSize() {
        return reason.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void pack(ByteBuf buffer) {
        packString(buffer, reason);
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
