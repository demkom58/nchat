package com.demkom58.nchat.server.data.config.serialized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

public class SerializedConfig {
    public String storageType = "sqlite";
    public int max_messages_length = 1000;
    public int messages_per_second = 3;
    public int connections_per_ip = 3;

    @JsonIgnore
    public void setMaxMessagesLength(final int max_messages_length) {
        this.max_messages_length = max_messages_length;
    }

    @JsonIgnore
    public int getMaxMessagesLength() {
        return max_messages_length;
    }

    @JsonIgnore
    public String getStorageType() {
        return storageType;
    }

    @JsonIgnore
    public void setStorageType(@NotNull final String storageType) {
        this.storageType = storageType;
    }

    @JsonIgnore
    public int getMessagesPerSecond() {
        return messages_per_second;
    }

    @JsonIgnore
    public void setMessagesPerSecond(final int messages_per_second) {
        this.messages_per_second = messages_per_second;
    }

    @JsonIgnore
    public int getConnectionsPerIp() {
        return connections_per_ip;
    }

    @JsonIgnore
    public void setConnectionsPerIp(final int connections_per_ip) {
        this.connections_per_ip = connections_per_ip;
    }
}
