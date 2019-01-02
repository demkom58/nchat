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

    private static final Logger LOGGER = LoggerFactory.getLogger("[SERVER]");

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

                    //.handler(new LoggingHandler(LogLevel.INFO))
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
       final Channel channel = user.getChannel();
       final ADisconnectPacket packet = new ADisconnectPacket(reason);

        channel.writeAndFlush(packet);
        LOGGER.info("ClientUser " + user.getNick() + "[" + user.getAddress() + "]" + " disconnected. Reason: " + reason);

        user.sendPacket(packet);
        removeUser(channel);
    }
    public void kickUser(Channel channel, String reason) {
        final User user = getUser(channel);
        if(user != null) {
            kickUser(user, reason);
            return;
        }
        final ADisconnectPacket packet = new ADisconnectPacket(reason);

        channel.writeAndFlush(packet);
        LOGGER.info("Guest[" + NetworkUtil.getAddress(channel) + "]" + " disconnected. Reason: " + reason);

        User.sendPacket(channel, packet);
        removeUser(channel);
    }
    public void removeUser(Channel channel) {
        getChannels().remove(channel);
        getRegisterMap().remove(channel);
        channel.close();
    }

    public void broadcast(String message) {
        final String rMessage = message + "\n";
        getRegisteredChannels().forEach(channel -> channel.writeAndFlush(rMessage));
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
        return LOGGER;
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
