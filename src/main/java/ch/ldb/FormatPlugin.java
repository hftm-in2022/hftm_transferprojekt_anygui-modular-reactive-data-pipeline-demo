// src\main\java\ch\ldb\FormatPlugin1.java

package ch.ldb;

public class FormatPlugin implements Plugin<String> {
    private final Observable<String> observableA = new Observable<>();
    private final Observable<String> observableB = new Observable<>();

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
            String transformed = transformPipelineA(data);
            observableA.emit(transformed);
        });
    }

    @Override
    public void setInputB(Observable<String> input) {
        input.subscribe(data -> {
            String transformed = transformPipelineB(data);
            observableB.emit(transformed);
        });
    }

    private String transformPipelineA(String data) {
        return "Formatted using FormatPlugin1 (Pipeline A): " + data;
    }

    private String transformPipelineB(String data) {
        return "Formatted using FormatPlugin1 (Pipeline B): " + data;
    }
}