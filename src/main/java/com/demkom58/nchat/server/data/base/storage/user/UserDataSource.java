package com.demkom58.nchat.server.data.base.storage.user;

import com.demkom58.nchat.server.data.base.storage.Storage;
import com.demkom58.nchat.server.network.User;
import org.jetbrains.annotations.NotNull;

public abstract class UserDataSource {

    public abstract void create(@NotNull final User user) throws Exception;

    public abstract boolean has(@NotNull final User user) throws Exception;

    public abstract void remove(@NotNull final User user) throws Exception;

    public abstract Storage get(@NotNull final User user) throws Exception;

}
