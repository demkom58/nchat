package com.demkom58.nchat.server.network.processors;

import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import com.demkom58.nchat.common.network.util.NetworkUtil;
import com.demkom58.nchat.server.Server;
import com.demkom58.nchat.server.network.ServerPacketProcessor;
import com.demkom58.nchat.server.network.User;
import io.netty.channel.Channel;
import org.slf4j.Logger;

public class ADisconnectPacketProcessor {

    public static void processADisconnectPacket(ADisconnectPacket packet, ServerPacketProcessor spp) {
        Logger logger = ServerPacketProcessor.LOGGER;
        Channel channel = spp.getChannel();

        Server server = Server.getServer();
        String reason = packet.getReason();

        String address = "["+ NetworkUtil.getAddress(channel)+"]";
        String log;

        if(!server.getRegisteredChannels().contains(channel)) {
            server.removeUser(channel);
            log = "Connection" + address + " has lost server.";
        } else {
            User user = server.getUser(channel);
            server.broadcast("[Server] " + user.getNick() + " has left!");
            server.removeUser(channel);
            log = "ClientUser " + user.getNick() + address + " has left server.";
        }
        log += " Reason: " + reason;
        logger.info(log);
    }
}
