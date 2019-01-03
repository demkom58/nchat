package com.demkom58.nchat.client.network;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.common.network.IPacketRegistry;
import com.demkom58.nchat.common.network.PacketRegistry;
import com.demkom58.nchat.common.network.packets.Packet;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientMessenger extends SocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger("[CLIENT]");

    private static LinkedBlockingQueue<String> messagesQueue = new LinkedBlockingQueue<>();
    private static ClientMessenger clientMessenger;
    private static boolean work;

    private IPacketRegistry packetRegistry;

    private String host;
    private int port;

    public ClientMessenger(String host, int port) {
        super();

        this.host = host;
        this.port = port;

        this.packetRegistry = new PacketRegistry();

        this.packetRegistry.registerPacket(CAuthPacket.class);
        this.packetRegistry.registerPacket(AMessagePacket.class);
        this.packetRegistry.registerPacket(ADisconnectPacket.class);
    }

    public void run() throws Exception {
        ClientMessenger.work = true;
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            connect(new InetSocketAddress(host, port));

            //Send register packet
            sendPacket(new CAuthPacket(User.getName(), Main.PROTOCOL_VERSION));

            while (work) {
                String message = messagesQueue.take().replaceAll("╥", "");
                setChannelFuture(sendPacket(new AMessagePacket(message)));
                User.addSentMessage();
            }

            if (getChannelFuture() != null)
                getChannelFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }

    public IPacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public ChannelFuture sendPacket(Packet packet) {
        if (getChannel() == null)
            return null;

        return getChannel().writeAndFlush(packet);
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

    public static ClientMessenger getClientMessenger() {
        return clientMessenger;
    }

    public static void close() {
        work = false;
    }

    public static void setup(String host, int port) {
        ClientMessenger.clientMessenger = new ClientMessenger(host, port);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
    
}
