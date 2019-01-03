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

    public FXMLLoader getFXMLLoaderAndExport(@NotNull final Class resourceAccessor,
                                                    @NotNull final String fxmlPath,
                                                    @NotNull final String remotePath) throws Exception {
        return new FXMLLoader(exportResource(resourceAccessor, fxmlPath, remotePath).toURI().toURL());
    }

    public File exportResource(@NotNull final Class resourceAccessor,
                                      @NotNull final String localPath,
                                      @NotNull final String remotePath) throws Exception {
        File distFile = new File(remotePath, localPath);

        if (!distFile.exists()) {
            distFile.getParentFile().mkdirs();
            exportResource(resourceAccessor, localPath, distFile);
        }

        return distFile;
    }

    private void exportResource(@NotNull final Class resourceAccessor,
                                       @NotNull final String localPath,
                                       @NotNull final File distFile) throws Exception {

        try (InputStream local = resourceAccessor.getResourceAsStream(localPath);
             OutputStream dist = new FileOutputStream(distFile)) {

            if (local == null)
                throw new Exception("Cannot get resource \"" + localPath + "\" from Jar file.");

            final byte[] buffer = new byte[4096];
            int readBytes;

            while ((readBytes = local.read(buffer)) > 0)
                dist.write(buffer, 0, readBytes);

        }
    }

    public boolean checkStyleVersion(String path, String currentVersion) throws Exception {
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
