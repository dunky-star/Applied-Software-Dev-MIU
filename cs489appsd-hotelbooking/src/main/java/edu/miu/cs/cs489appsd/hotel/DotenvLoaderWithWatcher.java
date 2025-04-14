package edu.miu.cs.cs489appsd.hotel;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DotenvLoaderWithWatcher {

    private static final Map<String, String> loadedVars = new HashMap<>();

    public static void loadAndWatch(String path) {
        load(path); // Initial load

        Thread watcherThread = new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path envDir = Paths.get(path).getParent();
                if (envDir == null) return;

                envDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take(); // Blocks until file is changed
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (changed.toString().equals(Paths.get(path).getFileName().toString())) {
                            System.out.println("[Dotenv] Detected change in .env. Reloading...");
                            load(path);
                        }
                    }
                    key.reset();
                }
            } catch (Exception e) {
                System.err.println("[Dotenv] Watcher failed: " + e.getMessage());
            }
        });

        watcherThread.setDaemon(true); // So it doesn't block shutdown
        watcherThread.start();
    }

    private static void load(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            loadedVars.clear();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim().replaceAll("^\"|\"$", "");

                    // Store in Java system properties (Spring will pick this up)
                    System.setProperty(key, value);
                    loadedVars.put(key, value);
                }
            }

            System.out.println("[Dotenv] Environment reloaded with variables: " + loadedVars.keySet());
        } catch (IOException e) {
            System.err.println("[Dotenv] Error reading .env: " + e.getMessage());
        }
    }
}

