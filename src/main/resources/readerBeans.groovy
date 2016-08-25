import au.com.bytecode.opencsv.CSVReader
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean
import rubiconproject.reader.CSVFileReader
import rubiconproject.reader.InputFileReaderFactory
import rubiconproject.reader.JsonFileReader

beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan' 'base-package': "rubiconproject"
    context.'property-placeholder'('location':'classpath:application.properties')

    inputStream(FileInputStream, "dummy"){ bean ->
        bean.scope = 'prototype'
    }

    inputStreamReader(InputStreamReader, inputStream) { bean ->
        bean.scope = 'prototype'
    }

    csvReader(CSVReader, inputStreamReader) { bean ->
        bean.scope = 'prototype'
    }
    csvFileReader(CSVFileReader, csvReader) { bean ->
        bean.scope = 'prototype'
    }

    objectMapper(ObjectMapper)

    jsonFileReader(JsonFileReader, "dummy", objectMapper) { bean ->
        bean.scope = 'prototype'
    }

    inputFileReaderFactory(ServiceLocatorFactoryBean) {
        serviceLocatorInterface = InputFileReaderFactory.class
    }
}