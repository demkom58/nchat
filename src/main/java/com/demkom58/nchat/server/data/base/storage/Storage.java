package com.demkom58.nchat.server.data.base.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class Storage {

    public abstract void insert(@NotNull final String key, @NotNull final Object value) throws Exception;

    public abstract Object get(@NotNull final String key) throws Exception;

    public abstract Map deserialize() throws Exception;

    public abstract void create() throws Exception;

    public abstract void destroy() throws Exception;

    public abstract boolean exists() throws Exception;
}
