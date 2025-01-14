// src\main\java\ch\ldb\TerminalPlugin.java
package ch.ldb;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TerminalPlugin implements Plugin<String> {
    private final Observable<String> output = new Observable<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public Observable<String> getOutput() {
        return output;
    }

    @Override
    public void setInput(Observable<String> input) {
        input.subscribe(data -> {
            System.out.println("Terminal Output: " + data);
        });
    }

    public void startReadingFromTerminal() {
        executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type something in the terminal:");
            while (true) {
                String input = scanner.nextLine();
                output.emit(input); // Emit terminal input
            }
        });
    }
}