
import org.springframework.beans.factory.config.ListFactoryBean
import org.springframework.beans.factory.config.MapFactoryBean
import rubiconproject.Worker
import rubiconproject.keywordservice.DummyKeywordService
import rubiconproject.keywordservice.InputDataKeywordsProvider
import rubiconproject.processor.FileListProvider
import rubiconproject.processor.InputDataProcessor
import rubiconproject.writer.FileOutput
import rubiconproject.writer.ResultPrinter

beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan' 'base-package': "rubiconproject"
    context.'property-placeholder'('location':'classpath:application.properties')

    importBeans "classpath:readerBeans.groovy"

    allowedFileExtensions(ListFactoryBean){
        sourceList = [".csv", ".json"]
    }

    fileListProvider(FileListProvider, '${input.directory}')


    dummyKeywordsMap(MapFactoryBean){
        sourceMap = [
                0: "sports,tennis,recreation",
                1: "japan,travel",
                2: "guitar,music",
        ]
    }

    keywordService(DummyKeywordService, dummyKeywordsMap)

    inputDataKeywordsProvider(InputDataKeywordsProvider, keywordService)

    inputDataProcessor(InputDataProcessor, fileListProvider, inputFileReaderProvider)

    output(FileOutput, '${output.file}')

    resultPrinter(ResultPrinter)

    mainWorker(Worker, inputDataProcessor, resultPrinter, output)
}