// src\main\java\ch\ldb\PluginFactory.java
package ch.ldb;

public class PluginFactory {
    private final ConfigLoader configLoader;

    public PluginFactory(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public Plugin<String> createInterfacePlugin() {
        String interfaceType = configLoader.getInterface();
        if ("1".equals(interfaceType)) {
            return new IOPlugin1(); // Injected dependency
        } else if ("2".equals(interfaceType)) {
            return new IOPlugin2(); // Injected dependency
        } else {
            throw new IllegalArgumentException("Unknown interface type: " + interfaceType);
        }
    }

    public Plugin<String> createFormatPlugin() {
        String formatType = configLoader.getFormat();
        if ("1".equals(formatType)) {
            return new FormatPlugin1(); // Injected dependency
        } else if ("2".equals(formatType)) {
            return new FormatPlugin2(); // Injected dependency
        } else {
            throw new IllegalArgumentException("Unknown format type: " + formatType);
        }
    }
}