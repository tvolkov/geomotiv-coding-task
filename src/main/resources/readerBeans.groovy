import au.com.bytecode.opencsv.CSVReader
import com.fasterxml.jackson.databind.ObjectMapper
import rubiconproject.reader.CSVFileReader
import rubiconproject.reader.JsonFileReader

beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan' 'base-package': "rubiconproject"
    context.'property-placeholder'('location':'classpath:application.properties')

    inputStream(FileInputStream, getClass().getResourceAsStream("/input1.csv")){ bean ->
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

    jsonFileReader(JsonFileReader, "input2.json", objectMapper) { bean ->
        bean.scope = 'prototype'
    }
}