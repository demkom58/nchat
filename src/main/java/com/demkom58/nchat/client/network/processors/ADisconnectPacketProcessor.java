package com.demkom58.nchat.client.network.processors;

import com.demkom58.nchat.client.network.ClientPacketProcessor;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import org.slf4j.Logger;

public class ADisconnectPacketProcessor {
    public static void processADisconnectPacket(ADisconnectPacket packet, ClientPacketProcessor cpp) {
        Logger logger = ClientPacketProcessor.logger;
        //TODO: Switch to disconnect screen.
    }
}
