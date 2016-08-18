package rubiconproject;

import org.springframework.beans.factory.annotation.Autowired;
import rubiconproject.processor.InputDataProcessor;
import rubiconproject.writer.Output;
import rubiconproject.writer.ResultPrinter;

public class Worker {
    private InputDataProcessor inputDataProcessor;
    private ResultPrinter resultPrinter;
    private Output output;

    @Autowired
    public Worker(InputDataProcessor inputDataProcessor, ResultPrinter resultPrinter, Output output) {
        this.inputDataProcessor = inputDataProcessor;
        this.resultPrinter = resultPrinter;
        this.output = output;
    }

    public void start(){
        output.output(resultPrinter.printResult(inputDataProcessor.processInputData()));
    }
}
