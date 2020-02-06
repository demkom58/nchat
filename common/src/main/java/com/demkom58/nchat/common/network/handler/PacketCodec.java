package com.demkom58.nchat.common.network.handler;

import com.demkom58.nchat.common.network.IPacketRegistry;
import com.demkom58.nchat.common.network.packets.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class PacketCodec extends ByteToMessageCodec<IPacket<?>> {
    private final PacketEncoder encoder;
    private final PacketDecoder decoder;
    
    public PacketCodec(IPacketRegistry packetRegistry) {
        this.encoder = new PacketEncoder();
        this.decoder = new PacketDecoder(packetRegistry);
    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, IPacket msg, ByteBuf out) throws Exception {
        encoder.encode(ctx, msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        decoder.decode(ctx, in, out);
    }
    
}
