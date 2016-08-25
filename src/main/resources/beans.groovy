
import org.springframework.beans.factory.config.ListFactoryBean
import org.springframework.beans.factory.config.MapFactoryBean
import org.springframework.core.io.ClassPathResource
import rubiconproject.Worker
import rubiconproject.keywordservice.DummyKeywordService
import rubiconproject.keywordservice.InputDataKeywordsProvider
import rubiconproject.processor.FileListProvider
import rubiconproject.processor.InputDataProcessor
import rubiconproject.processor.InputFileValidator
import rubiconproject.reader.CollectionLoader
import rubiconproject.writer.FileOutput
import rubiconproject.writer.ResultPrinter

beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan' 'base-package': 'rubiconproject'
    context.'property-placeholder'('location':'classpath:application.properties')


    def properties = new Properties()
    properties.load(new ClassPathResource('application.properties').inputStream);

    importBeans "classpath:readerBeans.groovy"

    allowedFileExtensions(ListFactoryBean){
        sourceList = [".csv", ".json"]
    }

    inputFileValidator(InputFileValidator)

    fileListProvider(FileListProvider, properties.'input.directory', inputFileValidator)


    dummyKeywordsMap(MapFactoryBean){
        sourceMap = [
                0: "sports,tennis,recreation",
                1: "japan,travel",
                2: "guitar,music",
        ]
    }

    keywordService(DummyKeywordService, dummyKeywordsMap)

    inputDataKeywordsProvider(InputDataKeywordsProvider, keywordService)

    beanAliasesMap(MapFactoryBean){
        sourceMap = [
                'csv': 'csvFileReader',
                'json': 'jsonFileReader',
        ]
    }

    collectionLoader(CollectionLoader, inputFileReaderFactory, beanAliasesMap)

    inputDataProcessor(InputDataProcessor, fileListProvider, inputDataKeywordsProvider, collectionLoader)

    output(FileOutput, properties.'output.file')

    resultPrinter(ResultPrinter, objectMapper)

    mainWorker(Worker, inputDataProcessor, resultPrinter, output)
}