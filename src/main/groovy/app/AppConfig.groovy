package app

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.Validate

@Slf4j
class AppConfig {

    @JsonProperty("http.port")
    int httpPort

    static AppConfig newInstance(File appConfigFile) throws IOException {
        Validate.isTrue(appConfigFile.exists(), "AppConfigFile not exists: ${appConfigFile.getAbsolutePath()}")
        log.debug('@Loading app.AppConfig')

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()).with {
            setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }

        AppConfig appConfig = objectMapper.readValue(appConfigFile, AppConfig.class)

        //=> Overide http.port properties from System Properties
        String httpPort = System.properties.getProperty('http.port', Integer.toString(appConfig.httpPort))
        appConfig.httpPort = Integer.parseInt(httpPort)
        return appConfig

    }
}
