package com.demkom58.nchat.common.network.packets;

import com.demkom58.nchat.common.network.handler.PacketEncoder;
import com.demkom58.nchat.common.network.processing.IPacketProcessor;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * This is a basic implementation of {@link IPacket} that you can extend for some
 * basic functions.
 * 
 * @param <PACKET_PROCESSOR> Same as {@link IPacket}
 * @see IPacket
 * @see IPacketProcessor
 */
public abstract class Packet<PACKET_PROCESSOR extends IPacketProcessor> implements IPacket<PACKET_PROCESSOR> {

    @Override
    public int getPacketSize() {
        return Byte.SIZE/8 + Short.SIZE/8 + getPayloadSize();
    }

    protected void packString(ByteBuf buf, String str) {
        String st = str.replace(PacketEncoder.getFrameSymbol(), "");
        byte[] bStr = st.getBytes(StandardCharsets.UTF_8);

        buf.writeShort(bStr.length);
        buf.writeBytes(bStr);
    }

    protected String unpackString(ByteBuf buf) {
        byte[] bMsg = new byte[buf.readShort()];
        buf.readBytes(bMsg);

        return new String(bMsg, StandardCharsets.UTF_8);
    }
}
