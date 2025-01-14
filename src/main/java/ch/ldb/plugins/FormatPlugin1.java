package ch.ldb.plugins;

import ch.ldb.core.Observable;

public class FormatPlugin1 implements Plugin<String> {
    private final Observable<String> output = new Observable<>();

    @Override
    public Observable<String> getOutput() {
        return output;
    }

    @Override
    public void setInput(Observable<String> input) {
        input.subscribe(data -> {
            String transformed = "Format Plugin 1: " + data;
            output.emit(transformed); // Emit transformed data
        });
    }
}
