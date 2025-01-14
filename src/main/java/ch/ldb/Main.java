package ch.ldb;

import ch.ldb.core.ConfigLoader;
import ch.ldb.core.PluginFactory;
import ch.ldb.plugins.FileInputPlugin;
import ch.ldb.plugins.Plugin;
import ch.ldb.plugins.TerminalPlugin;

public class Main {
    public static void main(String[] args) {
        // Load configuration
        String configFilePath = "src\\main\\java\\ch\\ldb\\.config.txt";
        ConfigLoader configLoader = new ConfigLoader(configFilePath);
        String inputFilePath = configLoader.get("inputPath");

        if (inputFilePath == null || inputFilePath.isEmpty()) {
            System.err.println("Error: 'inputPath' not found or is empty in the configuration file.");
            return;
        }

        // Initialize the FileInputPlugin
        FileInputPlugin fileInputPlugin = new FileInputPlugin();

        // Create the plugin factory
        PluginFactory pluginFactory = new PluginFactory(configLoader);

        // Dynamically create plugins
        Plugin<String> interfacePlugin = pluginFactory.createInterfacePlugin();
        Plugin<String> formatPlugin = pluginFactory.createFormatPlugin();
        TerminalPlugin terminalPlugin = new TerminalPlugin();

        // Wire the plugins together
        formatPlugin.setInput(interfacePlugin.getOutput()); // InterfacePlugin -> FormatPlugin
        terminalPlugin.setInput(formatPlugin.getOutput()); // FormatPlugin -> TerminalPlugin
        interfacePlugin.setInput(terminalPlugin.getOutput()); // TerminalPlugin -> InterfacePlugin

        // Start reading from the terminal
        terminalPlugin.startReadingFromTerminal();

        // Use the file path from configuration
        fileInputPlugin.readFromFile(inputFilePath);
    }
}
