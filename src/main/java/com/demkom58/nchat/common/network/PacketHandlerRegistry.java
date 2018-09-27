package com.demkom58.nchat.common.network;

import com.demkom58.nchat.common.network.packets.IPacket;
import com.demkom58.nchat.common.network.processing.IPacketProcessor;
import net.jodah.typetools.TypeResolver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketHandlerRegistry implements IPacketHandlerRegistry {
    private final List<IPacketProcessor> packetProcessors = new ArrayList<>();
    private final Map<Class<? extends IPacket>, IPacketProcessor> packetToMatcherMap = new HashMap<>();
    
    @Override
    public void registerPacketProcessor(IPacketProcessor packetProcessor) {
        if(packetProcessors.contains(packetProcessor)) {
            throw new IllegalArgumentException("PacketProcessor already registered!");
        }
        packetProcessors.add(packetProcessor);
    }

    @Override
    public IPacketProcessor getPacketProcessorFor(IPacket packet) {
        IPacketProcessor packetProcessor = packetToMatcherMap.get(packet.getClass());
        if(packetProcessor == null) {
            packetProcessor = findPacketProcessorForPacket(packet);
            packetToMatcherMap.put(packet.getClass(), packetProcessor);
        }
        return packetProcessor;
    }
    
    private IPacketProcessor findPacketProcessorForPacket(IPacket packet) {
        Type type = TypeResolver.resolveRawArgument(IPacket.class, packet.getClass());
        
        for(IPacketProcessor packetProcessor : packetProcessors) {
            if(((Class)type).isAssignableFrom(packetProcessor.getClass())) {
                return packetProcessor;
            }
        }
        throw new IllegalArgumentException("Unable to find PacketProcessor for " + packet.getClass().getName());
    }
}