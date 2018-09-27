package com.demkom58.nchat.common.network.processing;

import com.demkom58.nchat.common.network.packets.IPacket;

/**
 * A interface which is implemented by a class to state that it can process
 * a packet i.e. can do something with the contents.
 * <br />
 * Usually, a IPacketProcessor should implement a method in the form of
 * <code>public void processPacketTest(PacketTest packet)</code>
 * where "PacketTest" is replaced by the name of the IPacket you want to be
 * able to process. This method is then called by this network
 * {@link IPacket#processPacket(IPacketProcessor)}.
 * <br />
 * It is possible for one IPacketProcessor to implement multiple
 * <code>processPacket*()</code> methods.
 * 
 * @see IPacket
 */
public interface IPacketProcessor {
    public void sendPacket(IPacket packet);
}
