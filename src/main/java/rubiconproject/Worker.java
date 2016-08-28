package rubiconproject;

import org.springframework.beans.factory.annotation.Autowired;
import rubiconproject.processor.InputDataProcessor;
import rubiconproject.writer.FileOutput;
import rubiconproject.writer.ResultPrinter;

/**
 * main workflow
 */
public class Worker {
    private final InputDataProcessor inputDataProcessor;
    private final ResultPrinter resultPrinter;
    private final FileOutput output;

    @Autowired
    public Worker(InputDataProcessor inputDataProcessor, ResultPrinter resultPrinter, FileOutput output) {
        this.inputDataProcessor = inputDataProcessor;
        this.resultPrinter = resultPrinter;
        this.output = output;
    }

    void start(String inputDirectory, String outputFile){
        output.output(resultPrinter.printResult(inputDataProcessor.processInputData(inputDirectory)), outputFile);
    }
}
