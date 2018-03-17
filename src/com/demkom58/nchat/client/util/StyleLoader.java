package com.demkom58.nchat.client.util;

import com.demkom58.nchat.Main;
import com.demkom58.nchat.client.gui.AbstractController;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StyleLoader {
    private StyleLoader() { }

    public static FXMLLoader getFXMLLoaderAndExport(String fxml, String path) throws Exception {
        return new FXMLLoader(exportFile(fxml, path).toURI().toURL());
    }
    public static File exportFile(String file, String path) throws Exception {
        String fullpath = path + file;
        File distFile = new File(fullpath);

        if(!distFile.exists()) {
            distFile.getParentFile().mkdirs();
            exportResource(file, fullpath);
        }
        return distFile;
    }
    private static void exportResource(String resourceName, String destination) throws Exception {
        try (InputStream local = AbstractController.class.getResourceAsStream(resourceName); OutputStream dist = new FileOutputStream(destination)) {
            if (local == null) throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");

            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = local.read(buffer)) > 0) {
                dist.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean checkStyleVersion() throws Exception {
        File file = new File(Main.DATA_PATH + "style.ver");
        if(file.exists()) {
            byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
            String userStyleVer = new String(encoded, StandardCharsets.UTF_8);
            if(userStyleVer.equals(Main.STYLING_VERSION)) return true;
            return false;
        } else  {
            file.getParentFile().mkdirs();
            try (OutputStream target = new FileOutputStream(file)) {
                byte[] bytes = Main.STYLING_VERSION.getBytes(StandardCharsets.UTF_8);
                target.write(bytes, 0, bytes.length);
            } catch (Exception ex) {
                throw ex;
            }
            return false;
        }
    }

}
