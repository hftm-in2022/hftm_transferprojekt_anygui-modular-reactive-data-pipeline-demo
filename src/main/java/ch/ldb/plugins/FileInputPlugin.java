package ch.ldb.plugins;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ch.ldb.core.Observable;

public class FileInputPlugin implements Plugin<String> {
    private final Observable<String> output = new Observable<>();

    @Override
    public Observable<String> getOutput() {
        return output; // Emits data read from the file
    }

    @Override
    public void setInput(Observable<String> input) {
        // Not used in this implementation
    }

    // Read data from a .input.txt file and emit it to the observable
    public void readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Reading from file: " + filePath);
            while ((line = reader.readLine()) != null) {
                output.emit(line); // Emit each line to the observable
                System.out.println("Emitted: " + line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}
