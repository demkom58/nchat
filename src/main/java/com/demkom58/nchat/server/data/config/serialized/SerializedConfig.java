package com.demkom58.nchat.server.data.config.serialized;

import org.jetbrains.annotations.NotNull;

public class SerializedConfig {
    private String storageType = "sqlite";
    private int messages_per_second = 3;
    private int connections_per_ip = 3;

    @NotNull
    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(@NotNull final String storageType) {
        this.storageType = storageType;
    }

    public int getMessagesPerSecond() {
        return messages_per_second;
    }

    public void setMessagesPerSecond(final int messages_per_second) {
        this.messages_per_second = messages_per_second;
    }

    public int getConnectionsPerIp() {
        return connections_per_ip;
    }

    public void setConnectionsPerIp(final int connections_per_ip) {
        this.connections_per_ip = connections_per_ip;
    }
}
