// src\main\java\ch\ldb\TerminalPlugin.java
package ch.ldb;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TerminalPlugin implements Plugin<String> {
    private final Observable<String> observableA = new Observable<>(); // Forward flow
    private final Observable<String> observableB = new Observable<>(); // Reverse flow
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public Observable<String> getObservableA() {
        return observableA;
    }

    @Override
    public Observable<String> getObservableB() {
        return observableB;
    }

    @Override
    public void setInputA(Observable<String> input) {
        input.subscribe(data -> {
            System.out.println("Terminal Output (Forward): " + data);
        });
    }

    @Override
    public void setInputB(Observable<String> input) {
        input.subscribe(data -> {
            System.out.println("Terminal Output (Reverse): " + data);
        });
    }

    public void startReadingFromTerminal() {
        executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type something in the terminal:");
            while (true) {
                String input = scanner.nextLine();
                observableA.emit(input); // Emit terminal input (forward flow)
                observableB.emit(input); // Emit terminal input (reverse flow)
            }
        });
    }
}