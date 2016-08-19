package rubiconproject.reader;

import rubiconproject.model.Collection;

/**
 * this is a contract of reading input files: reader gets a collection name and returns collection of entries with that name
 */
public interface InputFileReader {
    Collection readFile(String collectionName);
}
