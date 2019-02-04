package com.demkom58.nchat.server.data.base;

import com.demkom58.nchat.server.data.base.storage.Storage;
import org.jetbrains.annotations.NotNull;

public abstract class DataSource {

    public abstract Storage create(@NotNull final String name) throws Exception;

    public abstract Storage get(@NotNull final String name) throws Exception;

    public abstract boolean exists(@NotNull final String name) throws Exception;

    public abstract void remove(@NotNull final String name) throws Exception;
}
