package com.demkom58.nchat.server.network.processors;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.common.network.handler.PacketEncoder;
import com.demkom58.nchat.common.network.packets.common.AMessagePacket;
import com.demkom58.nchat.server.Server;
import com.demkom58.nchat.server.network.ServerPacketProcessor;
import com.demkom58.nchat.server.network.User;
import io.netty.channel.Channel;
import org.slf4j.Logger;

import java.util.Collection;

public class AMessagePacketProcessor {

    public static void processAMessagePacket(AMessagePacket packet, ServerPacketProcessor spp) {
        Logger logger = ServerPacketProcessor.LOGGER;
        Channel channel = spp.getChannel();

        Server server = Server.getServer();
        String message = packet.getMessage().trim().replaceAll(PacketEncoder.getFrameSymbol(), "");
        User eventUser = server.getUser(channel);

        if(eventUser == null) {
            User.sendMessage(channel, "Register first please!");
            User.sendMessage(channel, "You was kicked!");

            server.kickUser(channel, "User wasn't registered.");
            return;
        }

        if(message.replaceAll(" ", "").replaceAll("\n", "").length() < 1) {
            eventUser.sendMessage("Too short message.");
            return;
        }


        Collection<User> users = server.getUsers();

        if(System.currentTimeMillis() < eventUser.getLastSentMessageTime()) {
            eventUser.sendMessage("[Server] Too many messages in second!");
            return;
        }
        eventUser.setLastSentMessageTime(System.currentTimeMillis() + (1000 / Main.MESSAGES_PER_SECOND));

        if (message.length() > Main.MAX_MESSAGE_LENGTH) {
            eventUser.sendMessage("Your message bigger then " + Main.MAX_MESSAGE_LENGTH + " symbols. Message was cancelled.");
            logger.info("User " + eventUser.getNick() + " tried to send message with length bigger then " + Main.MAX_MESSAGE_LENGTH);
            return;
        }

        for (User user : users)
            user.sendMessage("[" + eventUser.getNick() + "] " + message);

        logger.info("[" + eventUser.getAddress() + "] [" + eventUser.getNick() + "] " + message);
    }
}
