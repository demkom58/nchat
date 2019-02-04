package com.demkom58.nchat.server.data.config.base;

public abstract class ConfigBase {

    public abstract void write() throws Exception;

    public abstract void load() throws Exception;

    public abstract boolean exists() throws Exception;

    public abstract void create() throws Exception;
}
