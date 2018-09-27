package com.demkom58.nchat.client.network.processors;

import com.demkom58.nchat.client.network.ClientPacketProcessor;
import com.demkom58.nchat.common.network.packets.client.CAuthPacket;
import org.slf4j.Logger;

public class CAuthPacketProcessor {
    public static void processCAuthPacket(CAuthPacket packet, ClientPacketProcessor cpp) {
        final Logger logger = ClientPacketProcessor.LOGGER;

        logger.info("Process " + packet.getClass().getSimpleName() + ".");
        throw new UnsupportedOperationException("Not supported");
    }
}
