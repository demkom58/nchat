package com.demkom58.divine.gui;

import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GuiStyleLoader {

    public GuiStyleLoader() { }

    public @NotNull FXMLLoader getFXMLLoaderAndExport(@NotNull final Class resourceAccessor,
                                                      @NotNull final String fxmlPath,
                                                      @NotNull final String remotePath) throws Exception {
        return new FXMLLoader(exportResource(resourceAccessor, fxmlPath, remotePath).toURI().toURL());
    }

    public @NotNull File exportResource(@NotNull final Class resourceAccessor,
                                        @NotNull final String localPath,
                                        @NotNull final String remotePath) throws Exception {

        final File remoteResource = new File(remotePath, localPath);

        if (!remoteResource.exists()) {
            remoteResource.getParentFile().mkdirs();
            exportResource(resourceAccessor, localPath, remoteResource);
        }

        return remoteResource;
    }

    private void exportResource(@NotNull final Class resourceAccessor,
                                @NotNull final String localPath,
                                @NotNull final File distFile) throws Exception {

        try (InputStream localStream = resourceAccessor.getResourceAsStream(localPath);
             OutputStream remoteStream = new FileOutputStream(distFile)) {

            if (localStream == null)
                throw new Exception("Cannot get resource \"" + localPath + "\" from Jar file.");

            final byte[] buffer = new byte[4096];

            int readBytes;
            while ((readBytes = localStream.read(buffer)) > 0)
                remoteStream.write(buffer, 0, readBytes);

        }
    }

    public boolean checkStyleVersion(@NotNull final String path,
                                     @NotNull final String currentVersion) throws Exception {

        final File file = new File(path, "style.ver");

        if (file.exists()) {
            final byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
            final String userStyleVer = new String(encoded, StandardCharsets.UTF_8);

            return userStyleVer.equals(currentVersion);
        }

        file.getParentFile().mkdirs();
        try (OutputStream target = new FileOutputStream(file)) {
            byte[] bytes = currentVersion.getBytes(StandardCharsets.UTF_8);
            target.write(bytes, 0, bytes.length);
        }

        return false;

    }
}
