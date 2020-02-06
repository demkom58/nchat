package com.demkom58.nchat.common.network.processing;

import com.demkom58.nchat.common.network.packets.IPacket;
import io.netty.channel.Channel;

/**
 * See the interface {@link IPacketProcessor} for information.
 * 
 * @see IPacketProcessor
 */
public class PacketProcessor implements IPacketProcessor {
    private final Channel channel;

    public PacketProcessor(Channel channel) {
        this.channel = channel;
    }
    
    @Override
    public void sendPacket(IPacket packet) {
        channel.writeAndFlush(packet);
    }

    public Channel getChannel() {
        return channel;
    }
}
