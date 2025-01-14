package ch.ldb.plugins;

import ch.ldb.core.Observable;

public interface Plugin<T> {
    // Exposes the plugin's output as an observable
    Observable<T> getOutput();

    // Accepts input from another plugin
    void setInput(Observable<T> input);
}
