package com.demkom58.nchat.server.network.processors;

import com.demkom58.nchat.common.Environment;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import com.demkom58.nchat.server.Server;
import com.demkom58.nchat.server.network.ServerPacketProcessor;
import com.demkom58.nchat.server.network.User;
import io.netty.channel.Channel;
import org.slf4j.Logger;

import java.util.Collection;

public class CAuthPacketProcessor {
    public static void processCAuthPacket(CAuthPacket packet, ServerPacketProcessor spp) {
        final Logger logger = ServerPacketProcessor.LOGGER;
        final Channel channel = spp.getChannel();
        final Server server = Server.getServer();

        Collection<Channel> regChannels = server.getRegisteredChannels();
        if (regChannels.contains(channel)) {
            server.kickUser(channel, "Already registered.");
            return;
        }

        String protocol_version, nick;

        protocol_version = packet.getProtocolVersion();
        nick = packet.getNick();

        final User localUser = new User(channel, nick);
        if (!protocol_version.equals(Environment.PROTOCOL_VERSION)) {
            localUser.sendMessage("Server protocol version and client protocol version are different.");
            localUser.sendMessage("Your protocol version: " + protocol_version + ". Server protocol version: " + Environment.PROTOCOL_VERSION);
            localUser.kick("Different protocol versions. ClientMessenger version: " + protocol_version + ".");
            return;
        }

        Collection<User> users = server.getUsers();
        for (User cUser : users) if (cUser.getNick().equals(nick)) {
            User.sendMessage(channel, "This nickname is already taken.");
            User.sendMessage(channel, "You was kicked.");
            localUser.kick("This nick already taken.");
            return;
        }

        String voidName = nick.replace(" ", "");
        if (voidName.length() == 0 || nick.length() > 16) {
            localUser.kick("Bad nick format.");
            return;
        }

        server.registerUser(channel, localUser);
        localUser.sendMessage("Now you are connected to the chat server.");
        logger.info("User " + localUser.getNick() + "[" + localUser.getAddress() + "] has joined.");

        for (User cUser : users)
            if (cUser.getChannel() != channel) cUser.sendMessage("[Server] " + server.getUser(channel).getNick() + " has joined!");
    }
}
