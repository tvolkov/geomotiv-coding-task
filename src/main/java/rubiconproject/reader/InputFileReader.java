package rubiconproject.reader;

import rubiconproject.model.Collection;

/**
 * interface describes contract of reading input files: reader gets a coolection name and returns collections of entries with that name
 */
public interface InputFileReader {
    Collection readFile(String collectionName);
}
