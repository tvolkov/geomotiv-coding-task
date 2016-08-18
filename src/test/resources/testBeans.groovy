import au.com.bytecode.opencsv.CSVReader
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.config.ListFactoryBean
import rubiconproject.reader.CSVFileReader
import rubiconproject.processor.FileListProvider
import rubiconproject.reader.InputFileReaderProvider
import rubiconproject.reader.JsonFileReader
import rubiconproject.processor.InputDataProcessor

beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan' 'base-package': "rubiconproject"
//    context.'property-placeholder'('location':"file:*.properties")
    context.'property-placeholder'('location':"classpath:application.properties")

    allowedFileExtensions(ListFactoryBean){
        sourceList = [".csv", ".json"]
    }

    inputStreamReader(InputStreamReader, getClass().getResourceAsStream("/input1.csv")) { bean ->
        bean.scope = 'prototype'
    }

//    csvReader(CSVReader, new InputStreamReader(getClass().getResourceAsStream("/input1.csv"))) { bean ->
    csvReader(CSVReader, inputStreamReader) { bean ->
        bean.scope = 'prototype'
    }
    csvFileReader(CSVFileReader, csvReader) { bean ->
        bean.scope = 'prototype'
    }

    objectMapper(ObjectMapper)

    jsonFileReader(JsonFileReader, "input2.json", objectMapper) { bean ->
        bean.scope = 'prototype'
    }

    fileListProvider(FileListProvider, '${input.directory}')
    inputFileReaderProvider(InputFileReaderProvider)
    inputDataProcessor(InputDataProcessor, fileListProvider, inputFileReaderProvider)
}