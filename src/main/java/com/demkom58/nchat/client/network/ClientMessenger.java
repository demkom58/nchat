package com.demkom58.nchat.client.network;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.common.network.IPacketRegistry;
import com.demkom58.nchat.common.network.PacketRegistry;
import com.demkom58.nchat.common.network.packets.Packet;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

public class ClientMessenger {
    private static ClientMessenger clientMessenger;
    public static void prepare() {
        if(ClientMessenger.clientMessenger == null) ClientMessenger.clientMessenger = new ClientMessenger();
    }

    private static boolean work;
    private static LinkedBlockingQueue<String> messagesQueue = new LinkedBlockingQueue<>();
    private static final Logger logger = LoggerFactory.getLogger("[CLIENT]");

    private static Channel channel;
    private IPacketRegistry packetRegistry;

    private String host;
    private int port;

    public void run(String host, int port) throws Exception {
        ClientMessenger.work = true;
        this.host = host;
        this.port = port;

        this.packetRegistry = new PacketRegistry();

        this.packetRegistry.registerPacket(CAuthPacket.class);
        this.packetRegistry.registerPacket(AMessagePacket.class);
        this.packetRegistry.registerPacket(ADisconnectPacket.class);

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ClientInitializer());
            channel = bootstrap.connect(host, port).sync().channel();
            ChannelFuture channelFuture = null;

            //Send register packet
            register();

            while (work) {
                String message = messagesQueue.take();
                message = message.replaceAll("╥", "");
                channelFuture = sendPacket(new AMessagePacket().setMessage(message));
                ClientUser.addSentMessage();
            }
            if(channelFuture != null) channelFuture.sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private void register() {
        sendPacket(new CAuthPacket().setProtocolVersion(Main.PROTOCOL_VERSION).setNick(ClientUser.getName()));
    }

    public static ClientMessenger getClientMessenger() {
        return clientMessenger;
    }

    public static Channel getChannel() {
        return channel;
    }
    public IPacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public static void close() {
        work = false;
    }

    public ChannelFuture sendPacket(Packet packet) {
        if(channel == null) return null;
        ChannelFuture cf = getChannel().writeAndFlush(packet);
        return cf;
    }
    public void sendMessage(String message) {
        messagesQueue.offer(message);
    }

    public int getServerPort() {
        return port;
    }
    public String getServerHost() {
        return host;
    }

    public static Logger getLogger() {
        return logger;
    }
}
