package com.demkom58.nchat.server.data.yaml.user;

import com.demkom58.nchat.server.data.base.storage.Storage;
import com.demkom58.nchat.server.data.base.storage.user.UserDataSource;
import com.demkom58.nchat.server.data.yaml.YamlDataSource;
import com.demkom58.nchat.server.network.User;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class YamlUserDataSource extends UserDataSource {
    private final YamlDataSource dataSource;

    private final File usersFolder;

    private YamlUserDataSource(@NotNull final YamlDataSource dataSource, @NotNull final File usersFolder){
        this.dataSource = dataSource;

        this.usersFolder = usersFolder;
    }

    @Override
    public void create(@NotNull User user) throws Exception {
        if (has(user))
            return;

        dataSource.create(usersFolder + user.getNick());
    }

    @Override
    public boolean has(@NotNull User user) throws Exception {
        return dataSource.exists(usersFolder + user.getNick());
    }

    @Override
    public void remove(@NotNull User user) throws Exception {
        if (!has(user))
            return;

        final Storage storage = get(user);
        storage.destroy();
    }

    @Override
    public Storage get(@NotNull User user) throws Exception {
        return dataSource.get(usersFolder + user.getNick());
    }
}
