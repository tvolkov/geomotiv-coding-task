package rubiconproject.reader;

public interface InputFileReaderFactory {
    InputFileReader getReader(String readerName);
}
