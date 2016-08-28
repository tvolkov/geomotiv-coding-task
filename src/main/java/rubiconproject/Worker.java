package rubiconproject;

import org.springframework.beans.factory.annotation.Autowired;
import rubiconproject.processor.InputDataProcessor;
import rubiconproject.writer.Output;
import rubiconproject.writer.ResultPrinter;

/**
 * main workflow
 */
public class Worker {
    private final InputDataProcessor inputDataProcessor;
    private final ResultPrinter resultPrinter;
    private final Output output;

    @Autowired
    public Worker(InputDataProcessor inputDataProcessor, ResultPrinter resultPrinter, Output output) {
        this.inputDataProcessor = inputDataProcessor;
        this.resultPrinter = resultPrinter;
        this.output = output;
    }

    void start(){
        output.output(resultPrinter.printResult(inputDataProcessor.processInputData()));
    }
}
