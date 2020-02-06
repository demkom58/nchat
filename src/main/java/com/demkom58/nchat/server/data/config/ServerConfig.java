package com.demkom58.nchat.server.data.config;

import com.demkom58.nchat.server.data.config.base.ConfigBase;
import com.demkom58.nchat.server.data.config.serialized.SerializedConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class ServerConfig extends ConfigBase {
    private final File configFile;

    private SerializedConfig serializedConfig;

    public ServerConfig(@NotNull final File configFile){
        this.configFile = configFile;
    }

    @Override
    public void write() throws Exception {
        new ObjectMapper(new YAMLFactory()).writeValue(configFile, new SerializedConfig());
    }

    @Override
    public void load() throws Exception {
        serializedConfig = new ObjectMapper(new YAMLFactory()).readValue(configFile, SerializedConfig.class);
    }

    @Override
    public boolean exists() {
        return configFile.exists();
    }

    @Override
    public void create() throws IOException {
        if (exists())
            return;

        configFile.createNewFile();
    }

    @Nullable
    public SerializedConfig getSerialized() {
        return serializedConfig;
    }
}
