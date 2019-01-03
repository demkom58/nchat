package com.demkom58.nchat.server;

import com.demkom58.nchat.server.network.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Collection;

public abstract class SocketServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
    private static final ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final ServerBootstrap bootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public SocketServer() {
        final boolean epoll = Epoll.isAvailable();
        final boolean kQueue = KQueue.isAvailable();

        this.bossGroup = epoll ? new EpollEventLoopGroup() :
                (kQueue ? new KQueueEventLoopGroup() : new NioEventLoopGroup());

        this.workerGroup = epoll ? new EpollEventLoopGroup() :
                (kQueue ? new KQueueEventLoopGroup() : new NioEventLoopGroup());

        this.bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(epoll ? EpollServerSocketChannel.class :
                        (kQueue ? KQueueServerSocketChannel.class : NioServerSocketChannel.class))

                //.handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerInitializer())

                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    protected void bind(InetSocketAddress address) {
        try {
            this.bootstrap.bind(address).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void shutdown() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();

        try {
            this.bootstrap.config().group().terminationFuture().sync();
            this.bootstrap.config().childGroup().shutdownGracefully().sync();
        } catch (InterruptedException e) {
            LOGGER.error("Socket server shutdown process was interrupted!");
        }

    }

    public Collection<Channel> getChannels() {
        return CHANNELS;
    }

}
