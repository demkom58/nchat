package com.demkom58.nchat.server;

import com.demkom58.nchat.server.network.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public abstract class SocketServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    private final ServerBootstrap bootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public SocketServer() {
        final boolean epoll = Epoll.isAvailable();
        final boolean kQueue = KQueue.isAvailable();

        bossGroup = epoll ? new EpollEventLoopGroup() : kQueue
                ? new KQueueEventLoopGroup() : new NioEventLoopGroup();

        workerGroup = epoll ? new EpollEventLoopGroup() : kQueue
                ? new KQueueEventLoopGroup() : new NioEventLoopGroup();

        bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)

//                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerInitializer())

                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void bind(InetSocketAddress address) {
        try {
            bootstrap.bind(address).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();

        try {
            this.bootstrap.config().group().terminationFuture().sync();
            this.bootstrap.config().childGroup().shutdownGracefully().sync();
        } catch (InterruptedException e) {
            LOGGER.error("Socket server shutdown process was interrupted!");
        }

    }

}
