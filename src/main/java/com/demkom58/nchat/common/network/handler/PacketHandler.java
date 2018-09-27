package com.demkom58.nchat.common.network.handler;

import com.demkom58.nchat.common.network.IPacketHandlerRegistry;
import com.demkom58.nchat.common.network.packets.IPacket;
import com.demkom58.nchat.common.network.processing.IPacketProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class PacketHandler extends ChannelInboundHandlerAdapter {

    private Channel channel;
    private final IPacketHandlerRegistry packetProcessorRegistry;

    public PacketHandler(IPacketHandlerRegistry packetProcessorRegistry) {
        this.packetProcessorRegistry = packetProcessorRegistry;
    }
    
    public void writePacket(IPacket packet) {
        channel.write(packet);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        IPacket packet = (IPacket) msg;
        IPacketProcessor packetProcessor = packetProcessorRegistry.getPacketProcessorFor(packet);
        packet.processPacket(packetProcessor);
        ReferenceCountUtil.release(packet);
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
    }

    public Channel getChannel() {
        return channel;
    }
}