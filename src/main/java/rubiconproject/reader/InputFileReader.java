package rubiconproject.reader;

import rubiconproject.model.Entry;

import java.util.List;

/**
 General contract of reading input files
 */
public interface InputFileReader {
    List<Entry> readFile();
}
