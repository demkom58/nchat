package com.demkom58.nchat.client.network;

import org.jetbrains.annotations.NotNull;

public class User {

    @NotNull private String name;
    private int messagesSent = 0;

    public User(@NotNull final String name) {
        this.name = name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public int getMessagesSent() {
        return this.messagesSent;
    }

    public void addSentMessage() {
        this.messagesSent += 1;
    }

}
