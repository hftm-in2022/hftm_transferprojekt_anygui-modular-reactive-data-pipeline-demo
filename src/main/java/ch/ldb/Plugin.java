// src\main\java\ch\ldb\Plugin.java

package ch.ldb;

public interface Plugin<T> {
    // Observables for forward and reverse flows
    Observable<T> getObservableA(); // Forward flow
    Observable<T> getObservableB(); // Reverse flow

    // Methods to set input for forward and reverse flows
    void setInputA(Observable<T> input); // Set input for forward flow
    void setInputB(Observable<T> input); // Set input for reverse flow
}
