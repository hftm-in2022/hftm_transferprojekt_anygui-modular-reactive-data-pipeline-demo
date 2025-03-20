// src\main\java\ch\ldb\PluginFactory.java
package ch.ldb;

import java.nio.file.Path;

public class PluginFactory {
    private final ConfigLoader configLoader;

    public PluginFactory(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public Plugin<String> createInterfacePlugin() {
        String interfaceType = configLoader.getInterface();
        Path inputPath = Path.of(configLoader.getInputPath());
        Path outputPath = Path.of(configLoader.getOutputPath());

        if ("1".equals(interfaceType)) {
            IOPlugin ioPlugin = new IOPlugin(inputPath.toString(), outputPath.toString());
            System.out.println("Created IOPlugin with inputPath: " + inputPath + " and outputPath: " + outputPath);
            return ioPlugin;
        } else if ("2".equals(interfaceType)) {
            IOPlugin ioPlugin = new IOPlugin(inputPath.toString(), outputPath.toString());
            System.out.println("Created IOPlugin (variant 2) with inputPath: " + inputPath + " and outputPath: " + outputPath);
            return ioPlugin;
        } else {
            throw new IllegalArgumentException("Unknown interface: " + interfaceType);
        }
    }

    public Plugin<String> createFormatPlugin() {
        String formatType = configLoader.getFormat();
        if ("1".equals(formatType)) {
            System.out.println("Created FormatPlugin1");
            return new FormatPlugin(); // FormatPlugin1
        } else if ("2".equals(formatType)) {
            System.out.println("Created FormatPlugin2");
            return new FormatPlugin(); // FormatPlugin2
        } else {
            throw new IllegalArgumentException("Unknown format: " + formatType);
        }
    }
}