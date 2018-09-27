package com.demkom58.nchat.server;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.common.network.IPacketRegistry;
import com.demkom58.nchat.common.network.PacketRegistry;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import com.demkom58.nchat.common.network.util.NetworkUtil;
import com.demkom58.nchat.server.network.ServerInitializer;
import com.demkom58.nchat.server.network.User;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class Server {

    public static synchronized void start(List<String> args) throws Exception {
        if(Server.server != null) return;
        Server.server = new Server(Main.PORT);
    }

    private static Server server;

    private static final Logger logger = LoggerFactory.getLogger("[SERVER]");

    private final static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final static Map<Channel, User> regMap = new WeakHashMap<>();

    private EventLoopGroup bossGroup;
    private EventLoopGroup workersGroup;

    private IPacketRegistry packetRegistry;

    private int port;

    private Server(int port) throws Exception {
        Server.server = this;
        this.port = port;
        run();
    }

    private void run() throws Exception {
        getLogger().info("Starting server...");
        this.packetRegistry = new PacketRegistry();
        this.packetRegistry.registerPacket(CAuthPacket.class);
        this.packetRegistry.registerPacket(AMessagePacket.class);
        this.packetRegistry.registerPacket(ADisconnectPacket.class);

        this.bossGroup = new NioEventLoopGroup(1);
        this. workersGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap networkServer = new ServerBootstrap();
            networkServer
                    .group(bossGroup, workersGroup)
                    .channel(NioServerSocketChannel.class)

                    .handler(new LoggingHandler(LogLevel.INFO)) //DEBUG LOGGER
                    .childHandler(new ServerInitializer())

                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            getLogger().info("Waiting for connections...");
            networkServer.bind(port).sync().channel().closeFuture().sync();
        } finally {
            shutdown();
        }
    }

    public User getUser(Channel channel) {
        return getRegisterMap().get(channel);
    }
    public void registerUser(Channel channel, User user) {
        getRegisterMap().put(channel, user);
    }

    public void kickUser(User user, String reason) {
        Channel channel = user.getChannel();
        channel.writeAndFlush(new ADisconnectPacket().setReason(reason));
        String nick = user.getNick();
        String address = user.getAddress();
        System.out.println("ClientUser " + nick + "[" + address + "]" + " disconnected. Reason: " + reason);
        user.sendPacket(new ADisconnectPacket().setReason(reason));
        removeUser(channel);
    }
    public void kickUser(Channel channel, String reason) {
        User user = getUser(channel);
        if(user != null) {
            kickUser(user, reason);
            return;
        }

        channel.writeAndFlush(new ADisconnectPacket().setReason(reason));
        String address = NetworkUtil.getAddress(channel);
        System.out.println("Guest[" + address + "]" + " disconnected. Reason: " + reason);
        User.sendPacket(channel, new ADisconnectPacket().setReason(reason));
        removeUser(channel);
    }
    public void removeUser(Channel channel) {
        getChannels().remove(channel);
        getRegisterMap().remove(channel);
        channel.close();
    }

    public void broadcast(String message) {
        String rMessage = message + "\r\n";
        for(Channel channel : getRegisteredChannels()) channel.writeAndFlush(rMessage);
    }

    public void shutdown(String reason) {
        broadcast(reason);
        this.bossGroup.shutdownGracefully();
        this.workersGroup.shutdownGracefully();
    }
    public void shutdown() {
        shutdown("Server has been disabled.");
    }

    public Logger getLogger() {
        return logger;
    }
    public static Server getServer() {
        return Server.server;
    }

    public IPacketRegistry getPacketRegistry() {
        return packetRegistry;
    }
    public Collection<User> getUsers() {
        return getRegisterMap().values();
    }
    public Collection<Channel> getChannels() {
        return channels;
    }
    public Collection<Channel> getRegisteredChannels() {
        return getRegisterMap().keySet();
    }
    private Map<Channel, User> getRegisterMap() {
        return regMap;
    }


}
