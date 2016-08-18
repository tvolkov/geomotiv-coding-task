package rubiconproject.writer;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Slf4j
public class FileOutput implements Output {
    private final String outputFile;

    public FileOutput(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void printOutput(String content) {
        try (PrintWriter printWriter = new PrintWriter(new File(outputFile))){
            printWriter.println(content);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            log.error("File not found!");
        }
    }
}
