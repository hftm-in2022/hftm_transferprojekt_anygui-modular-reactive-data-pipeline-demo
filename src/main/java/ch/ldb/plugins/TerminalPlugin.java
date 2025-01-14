package ch.ldb.plugins;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.ldb.core.Observable;

public class TerminalPlugin implements Plugin<String> {
    private final Observable<String> output = new Observable<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public Observable<String> getOutput() {
        return output; // Emits data entered in the terminal
    }

    @Override
    public void setInput(Observable<String> input) {
        // Subscribe to the input observable and print the data to the terminal
        input.subscribe(data -> {
            System.out.println("Terminal Output: " + data);
        });
    }

    // Start reading input from the terminal and emit it to the output observable
    public void startReadingFromTerminal() {
        executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type something in the terminal (it will be saved to output.txt):");
            try {
                while (true) {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        System.out.println("Scanner finished.");
                        break; // Exit the loop if "Enter" is pressed without input
                    }
                    output.emit(input); // Emit the input to the observable
                }
            } finally {
                scanner.close(); // Close the scanner
                executor.shutdown(); // Shutdown the executor service
            }
        });
    }
}
