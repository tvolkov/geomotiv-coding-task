package rubiconproject.reader;

import au.com.bytecode.opencsv.CSVReader;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class CSVFileReader implements InputFileReader {
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int MOBILE_INDEX = 2;
    private static final int SCORE_INDEX = 3;

    private final CSVReader csvReader;

    CSVFileReader(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    @Override
    public Collection readFile(final String collectionId) {
        List<Entry> resultList = new ArrayList<>();
        try {
            String[] line;
            while ((line = csvReader.readNext()) != null){
                resultList.add(new Entry(line[ID_INDEX], line[NAME_INDEX], line[MOBILE_INDEX], line[SCORE_INDEX]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while parsing file: " + e.getMessage());
        }
        return new Collection(collectionId, resultList);
    }
}
