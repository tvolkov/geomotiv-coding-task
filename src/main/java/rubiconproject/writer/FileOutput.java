package rubiconproject.writer;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Slf4j
public class FileOutput {

    public void output(String content, String outputFile) {
        try (PrintWriter printWriter = new PrintWriter(new File(outputFile))){
            printWriter.println(content);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            log.error("File not found!");
        }
    }
}
