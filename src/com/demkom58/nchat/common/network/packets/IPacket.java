package com.demkom58.nchat.common.network.packets;

import com.demkom58.nchat.common.network.PacketHandlerRegistry;
import com.demkom58.nchat.common.network.processing.IPacketProcessor;
import io.netty.buffer.ByteBuf;

/**
 * A packet is a holder for data which can be transmitted over the network.
 * It should be registered at a {@link  PacketHandlerRegistry}
 * so that it can be decoded.
 * 
 * @param <PACKET_PROCESSOR> a implementation of {@link IPacketProcessor} which
 * can process a packet
 * @see IPacketProcessor
 * @see com.demkom58.nchat.common.network.PacketRegistry
 */
public interface IPacket<PACKET_PROCESSOR extends IPacketProcessor> {
    /**
     * The packetId is used to identify and decode a packet. It can only be
     * one byte long!
     * 
     * @return packetId
     */
    short getId();
    
    /**
     * The byte-size of the whole packet including the id (byte), the size
     * of the payload (short) + {@link #getPayloadSize()}.
     * 
     * @return the whole size of packet in bytes
     * @see #getPayloadSize() 
     */
    int getPacketSize();

    /**
     * Returns the size of the content of the packet in bytes.
     *
     * @return the size of the contents in bytes.
     */
    int getPayloadSize();
    
    /**
     * Encodes the contents of the packet into the {@link ByteBuf} given by
     * parameter.
     * 
     * @param buffer where the contents of the packet is packed into
     * @see #unpack(ByteBuf)
     * @see ByteBuf
     */
    void pack(ByteBuf buffer);
    
    /**
     * Decodes the contents of the {@link ByteBuf} back into a packet. It is the
     * revese operation of {@link #pack(ByteBuf)}. You need to make sure that
     * the contents are decoded in the same order as they are encoded!
     * 
     * @param buffer where the contents of the network are stored
     * @see #pack(ByteBuf)
     * @see ByteBuf
     */
    void unpack(ByteBuf buffer);
    
    /**
     * When a packet is decoded this method is called. It needs to call
     * its apropriate processing method that is defined in the {@link IPacketProcessor}.
     * 
     * @param packetProcessor the {@link IPacketProcessor} with an apropriate
     * mehtod for this packet
     * 
     * @see IPacketProcessor
     */
    void processPacket(PACKET_PROCESSOR packetProcessor);
}
