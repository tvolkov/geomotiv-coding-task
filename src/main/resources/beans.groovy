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
    context.'property-placeholder'('location':'classpath:application.properties')

    allowedFileExtensions(ListFactoryBean){
        sourceList = [".csv", ".json"]
    }

    inputStreamReader(InputStreamReader, getClass().getResourceAsStream("/input1.csv")) { bean ->
        bean.scope = 'prototype'
    }

//    csvReader(CSVReader, new InputStreamReader(getClass().getResourceAsStream("/input/input1.csv"))) { bean ->
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
    inputDataProcessor(InputDataProcessor, fileListProvider, inputFileReaderProvider)

    output(FileOutput, '${output.file}')

    resultPrinter(ResultPrinter)

    mainWorker(Worker, inputDataProcessor, resultPrinter, output)
}