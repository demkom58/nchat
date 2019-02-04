package com.demkom58.nchat.client.util;

import com.demkom58.nchat.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataIP {

    private static final String SRVS = Main.CLIENT_DATA_PATH + "srv.list";

    public static void saveIP(String ip) {
        try {
            File file = new File(SRVS);
            createFileIfNotExist(file);

            List<String> ips = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            boolean exist = ips.contains(ip);

            for(String fip : ips) {
                writer.write(fip);
                writer.newLine();
            }

            if(!exist) writer.write(ip);

            writer.flush();
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void saveIPList(List<String> ips) {
        try {
            File file = new File(SRVS);

            createFileIfNotExist(file);
            FileWriter writer = new FileWriter(SRVS);
            for (String str : ips) writer.write(str + "\n");
            writer.close();

        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<String> loadIPList() {
        try {
            File file = new File(SRVS);
            createFileIfNotExist(file);

            List<String> ips = new ArrayList<>();

            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) ips.add(scanner.nextLine());

            return ips;
        } catch (IOException e) { e.printStackTrace(); }
        return new ArrayList<>();
    }

    private static boolean createFileIfNotExist(File file) {
        try {
            if (!file.exists()) return file.createNewFile();
            return false;
        } catch (IOException ex) { return false; }
    }
}
