package com.demkom58.nchat.server.network;

import com.demkom58.nchat.common.network.IPacketHandlerRegistry;
import com.demkom58.nchat.common.network.PacketHandlerRegistry;
import com.demkom58.nchat.common.network.handler.PacketCodec;
import com.demkom58.nchat.common.network.handler.PacketEncoder;
import com.demkom58.nchat.server.Server;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        IPacketHandlerRegistry packetProcessorRegistry = new PacketHandlerRegistry();
        packetProcessorRegistry.registerPacketProcessor(new ServerPacketProcessor(ch));

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("deflater", ZlibCodecFactory.newZlibEncoder(ZlibWrapper.ZLIB));
        pipeline.addLast("inflater", ZlibCodecFactory.newZlibDecoder(ZlibWrapper.ZLIB));

        //TODO: timeout

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(30000, Unpooled.wrappedBuffer(PacketEncoder.getSymbolBytes())));
        pipeline.addLast("codec", new PacketCodec(Server.getServer().getPacketRegistry()));
        pipeline.addLast("handler", new ServerPacketHandler(packetProcessorRegistry));
    }
}
