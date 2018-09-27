package com.demkom58.nchat.common.network.handler;

import com.demkom58.nchat.common.network.IPacketRegistry;
import com.demkom58.nchat.common.network.packets.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    private final IPacketRegistry packetRegistry;
    
    private IPacket prototype = null;
    private short packetSize = -1;
    
    public PacketDecoder(IPacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        isPacketPrototypeReady(in);
        isPacketSizeReady(in);

        try {
            buildAndPassPacket(in, out);
        } finally {
            prototype = null;
            packetSize = -1;
        }
    }
    
    private void buildAndPassPacket(ByteBuf in, List<Object> out) {
        prototype.unpack(in);
        out.add(prototype);
    }
    
    private boolean isPacketPrototypeReady(ByteBuf in) {
        if(prototype == null) {
            if(in.readableBytes() >= 1) {
                prototype = packetRegistry.getNewPacketInstance(in.readShort());
            } else {
                return false;
            }
        }
        return true;
    }
    
    private boolean isPacketSizeReady(ByteBuf in) {
        if(packetSize == -1) {
            if(in.readableBytes() >= 2) {
                packetSize = in.readShort();
            } else {
                return false;
            }
        }
        return true;
    }
}