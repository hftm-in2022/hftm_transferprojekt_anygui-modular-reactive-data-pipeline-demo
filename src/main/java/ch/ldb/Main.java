// src\main\java\ch\ldb\Main.java
package ch.ldb;

public class Main {
    public static void main(String[] args) {
        // Load configuration
        String configFilePath = "src\\main\\java\\ch\\ldb\\.config.txt";
        ConfigLoader configLoader = new ConfigLoader(configFilePath);

        // Create the plugin factory
        PluginFactory pluginFactory = new PluginFactory(configLoader);

        // Dynamically create plugins
        Plugin<String> ioPlugin = pluginFactory.createInterfacePlugin(); // IOPlugin
        Plugin<String> formatPlugin = pluginFactory.createFormatPlugin(); // FormatPlugin
        TerminalPlugin terminalPlugin = new TerminalPlugin(); // TerminalPlugin

        // Wire the plugins together
        // Pipeline A: IO -> Format -> Terminal
        formatPlugin.setInputA(ioPlugin.getObservableA()); // IOPlugin -> FormatPlugin
        terminalPlugin.setInputA(formatPlugin.getObservableA()); // FormatPlugin -> TerminalPlugin

        // Pipeline B: Terminal -> Format -> IO
        formatPlugin.setInputB(terminalPlugin.getObservableB()); // TerminalPlugin -> FormatPlugin
        ioPlugin.setInputB(formatPlugin.getObservableB()); // FormatPlugin -> IOPlugin

        // Start reading from the terminal
        terminalPlugin.startReadingFromTerminal();
    }
}