package com.demkom58.nchat.server.data.yaml;

import com.demkom58.nchat.server.data.base.DataSource;
import com.demkom58.nchat.server.data.base.storage.Storage;
import com.demkom58.nchat.server.data.yaml.storage.YamlStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class YamlDataSource extends DataSource {
    public static ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
    public static String YAML_FILE_PREFIX = ".yml";

    private final File rootFolder;

    public YamlDataSource(@NotNull final File rootFolder){
        this.rootFolder = rootFolder;
    }

    @Override
    public Storage create(@NotNull String name) throws Exception {
        final Storage outputStorage = get(name);
        outputStorage.create();

        return outputStorage;
    }

    @Override
    public Storage get(@NotNull String name) throws Exception {
        return new YamlStorage(new File(rootFolder + name + YAML_FILE_PREFIX));
    }

    @Override
    public boolean exists(@NotNull String name) throws Exception {
        return get(name).exists();
    }

    @Override
    public void remove(@NotNull String name) throws Exception {
        final Storage storage = get(name);

        storage.destroy();
    }

    @NotNull
    public File getRootFolder() {
        return rootFolder;
    }
}
