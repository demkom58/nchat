package com.demkom58.nchat.common.network;

import com.demkom58.nchat.common.network.packets.IPacket;
import com.demkom58.nchat.common.network.processing.IPacketProcessor;

/**
 * At this class, network can be registered to be able to decode them. It can
 * (should) be used across multiple {@link io.netty.channel.Channel}s that use
 * the same Packets to save on memory.
 * 
 * @see IPacketProcessor
 */
public interface IPacketRegistry {
    void registerPacket(Class<? extends IPacket> packetClass);

    IPacket getNewPacketInstance(short packetId);

    boolean isIdRegistered(short packetId);
}
