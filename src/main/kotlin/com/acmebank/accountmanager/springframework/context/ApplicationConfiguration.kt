package com.acmebank.accountmanager.springframework.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
// import org.zalando.logbook.autoconfigure.LogbookAutoConfiguration

@Configuration
@ComponentScan(
    basePackages = [
        "com.acmebank.accountmanager.data.repository", "com.acmebank.accountmanager.rs",
        "com.acmebank.accountmanager.service"]
)
@EnableJpaRepositories("com.acmebank.accountmanager.data.repository")
@EntityScan("com.acmebank.accountmanager.data.domain")
/*@ImportAutoConfiguration(LogbookAutoConfiguration::class)*/
class ApplicationConfiguration {

    @Bean
    protected fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .findAndRegisterModules()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    }

}