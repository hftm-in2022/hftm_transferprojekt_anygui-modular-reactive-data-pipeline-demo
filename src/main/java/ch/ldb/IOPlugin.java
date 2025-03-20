// src\main\java\ch\ldb\IOPlugin1.java


package ch.ldb;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOPlugin implements Plugin<String> {
    private final Observable<String> observableA = new Observable<>(); // Emits data read from the input file
    private final Observable<String> observableB = new Observable<>(); // Receives data to write to the output file

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final String inputFilePath;  // Path to the input file
    private final String outputFilePath; // Path to the output file

    // Constructor to initialize input and output file paths
    public IOPlugin(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        watchInputFile(); // Start monitoring the input file
    }

    // Watches the input file for changes and emits its content via observableA
    private void watchInputFile() {
        executor.submit(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(inputFilePath).getParent();

                if (path == null) {
                    path = Paths.get(".").toAbsolutePath().normalize();
                }

                path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().equals(Paths.get(inputFilePath).getFileName().toString())) {
                            String content = Files.readString(Paths.get(inputFilePath));
                            observableA.emit(content); // Emit file content (forward flow)
                        }
                    }
                    key.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Observable<String> getObservableA() {
        return observableA; // Forward flow: Emits data read from the input file
    }

    @Override
    public Observable<String> getObservableB() {
        return observableB; // Reverse flow: Receives data to write to the output file
    }

    @Override
    public void setInputA(Observable<String> input) {
        // Not used in this plugin since Pipeline A is for emitting data from the input file
        throw new UnsupportedOperationException("Pipeline A is read-only for IOPlugin.");
    }

    @Override
    public void setInputB(Observable<String> input) {
        // Pipeline B: Receives data from the format plugin and writes it to the output file
        input.subscribe(data -> {
            try {
                writeToFile(data);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        });
    }

    // Writes data to the output file
    private void writeToFile(String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write(data);
            writer.newLine();
        }
    }
}