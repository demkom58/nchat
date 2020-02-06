package com.demkom58.nchat.common.network;

import com.demkom58.nchat.common.network.packets.IPacket;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry implements IPacketRegistry {
    private final Map<Short, Class<? extends IPacket<?>>> idToPacketMap = new HashMap<>();

    @Override
    public void registerPacket(Class<? extends IPacket<?>> packetClass) throws IllegalArgumentException {
        IPacket<?> packet = getNewPacketInstanceReflective(packetClass);
        short packetId = packet.getId();

        if (packetId > Byte.MAX_VALUE || packetId < Byte.MIN_VALUE) {
            throw new IllegalArgumentException("PacketId needs to be in byte-range for " + packetClass.getName());
        }

        if (isIdRegistered(packetId)) {
            throw new IllegalArgumentException(packetClass.getName() + ": PacketId " + packetId + " is already registered by " + idToPacketMap.get(packetId) + "!");
        }

        idToPacketMap.put(packetId, packetClass);
    }

    @Override
    public IPacket<?> getNewPacketInstance(short packetId) {
        if (!isIdRegistered(packetId)) throw new IllegalArgumentException("PacketId is not registered!");

        return getNewPacketInstanceReflective(idToPacketMap.get(packetId));
    }

    private IPacket<?> getNewPacketInstanceReflective(Class<? extends IPacket<?>> packetClass) {
        try {
            return packetClass.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Unable to instantiate " + packetClass.getName() + ". Did you forget a default constructor?", e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Unable to instantiate " + packetClass.getName() + ".", e);
        }
    }

    @Override
    public boolean isIdRegistered(short packetId) {
        return idToPacketMap.containsKey(packetId);
    }
}
