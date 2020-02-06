package com.demkom58.nchat.server.data.yaml.storage;

import com.demkom58.nchat.server.data.base.storage.Storage;
import com.demkom58.nchat.server.data.yaml.YamlDataSource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class YamlStorage extends Storage {
    private final File file;

    public YamlStorage(@NotNull final File file){
        this.file = file;
    }

    @Override
    public void insert(@NotNull String key, @NotNull Object value) throws IOException {
        final Map<Object, Object> fileContent = YamlDataSource.YAML_MAPPER.readValue(file, Map.class);
        fileContent.putIfAbsent(key, value);

        YamlDataSource.YAML_MAPPER.writeValue(file, fileContent);
    }

    @Override
    public Object get(@NotNull String key) throws Exception {
        return deserialize().get(key);
    }

    @Override
    public Map deserialize() throws Exception {
        return YamlDataSource.YAML_MAPPER.readValue(file, Map.class);
    }

    @Override
    public void create() throws Exception {
        if (exists())
            return;

        file.createNewFile();
    }

    @Override
    public void destroy() throws Exception {
        if (!exists())
            return;

        Files.delete(file.toPath());
    }

    @Override
    public boolean exists() throws Exception {
        return file.exists();
    }
}
