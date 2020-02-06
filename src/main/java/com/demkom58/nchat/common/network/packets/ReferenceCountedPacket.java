package com.demkom58.nchat.common.network.packets;

import com.demkom58.nchat.common.network.processing.IPacketProcessor;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;

public abstract class ReferenceCountedPacket<PACKET_PROCESSOR extends IPacketProcessor> extends Packet<PACKET_PROCESSOR> implements ReferenceCounted {
    private final ReferenceCounted referenceCounter = new AbstractReferenceCounted() {
        @Override
        public ReferenceCounted touch(Object hint) {
            return null;
        }

        @Override
        protected void deallocate() {
            ReferenceCountedPacket.this.deallocate();
        }
    };
    
    @Override
    public int refCnt() {
        return referenceCounter.refCnt();
    }
    
    @Override
    public ReferenceCounted retain() {
        referenceCounter.retain();

        return this;
    }
    
    @Override
    public ReferenceCounted retain(int increment) {
        referenceCounter.retain(increment);

        return this;
    }
    
    @Override
    public boolean release() {
        return referenceCounter.release();
    }
    
    @Override
    public boolean release(int decrement) {
        return referenceCounter.release(decrement);
    }
    
    protected abstract void deallocate();
}
