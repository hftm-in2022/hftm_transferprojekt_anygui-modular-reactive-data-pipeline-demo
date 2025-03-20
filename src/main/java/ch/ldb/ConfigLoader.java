// src\main\java\ch\ldb\ConfigLoader.java
package ch.ldb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    private final Map<String, String> config = new HashMap<>();

    public ConfigLoader(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return config.get(key);
    }

    public String getInterface() {
        return get("ioPlugin");
    }

    public String getFormat() {
        return get("formatPlugin");
    }

    public String getOutputPath() {
        return get("outputPath");
    }

    public String getInputPath() {
        return get("inputPath");
    }

    public void printConfig() {
        System.out.println("Loaded configuration:");
        config.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}