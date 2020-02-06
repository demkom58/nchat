package com.demkom58.nchat.common.network;

import com.demkom58.nchat.common.network.packets.IPacket;
import com.demkom58.nchat.common.network.processing.IPacketProcessor;

/**
 * Here, {@link IPacketProcessor}s can be registered to be available for
 * processing decoded {@link IPacket}s.
 * Every {@link io.netty.channel.Channel} needs its own Registry because most
 * network depend/operated on their {@link io.netty.channel.Channel}.
 * 
 * @see io.netty.channel.Channel
 * @see IPacket
 * @see IPacketProcessor
 */
public interface IPacketHandlerRegistry {
    void registerPacketProcessor(IPacketProcessor packetProcessor);
    
    /**
     * Searches for a matching {@link IPacketProcessor} that can handle a
     * specific {@link IPacket}
     * 
     * @param packet a {@link IPacket} to get the {@link IPacketProcessor} for
     * @return the appropriate {@link IPacketProcessor}
     */
    <T extends IPacketProcessor> T getPacketProcessorFor(IPacket<T> packet);
}
