import au.com.bytecode.opencsv.CSVReader
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.config.ListFactoryBean
import rubiconproject.Worker
import rubiconproject.reader.CSVFileReader
import rubiconproject.processor.FileListProvider
import rubiconproject.reader.JsonFileReader
import rubiconproject.processor.InputDataProcessor
import rubiconproject.writer.ConsoleOutput
import rubiconproject.writer.FileOutput
import rubiconproject.writer.ResultPrinter

beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan' 'base-package': "rubiconproject"
    context.'property-placeholder'('location':"file:*.properties")

    allowedFileExtensions(ListFactoryBean){
        sourceList = [".csv", ".json"]
    }

    csvReader(CSVReader, new InputStreamReader(getClass().getResourceAsStream("/input1.csv"))) { bean ->
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
    inputDataProcessor(InputDataProcessor, fileListProvider, inputFileReaderProvider)

    if ('${print.location}' == 'console'){
        output(ConsoleOutput)
    } else if ('${print.location}' == 'file') {
        output(FileOutput)
    } else {
        throw new RuntimeException("Invalid print.location value")
    }

    resultPrinter(ResultPrinter)

    mainWorker(Worker, inputDataProcessor, resultPrinter, output)
}