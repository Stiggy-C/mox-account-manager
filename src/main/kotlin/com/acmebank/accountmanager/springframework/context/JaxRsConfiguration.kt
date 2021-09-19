package com.acmebank.accountmanager.springframework.context

import com.fasterxml.jackson.databind.ObjectMapper
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
// import org.zalando.logbook.Logbook
// import org.zalando.logbook.jaxrs.LogbookServerFilter

@Configuration
class JaxRsConfiguration {

    @Bean
    protected fun jacksonJaxbJsonProvider(objectMapper: ObjectMapper): JacksonJaxbJsonProvider {
        return JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS)
    }

    /*@Bean
    protected fun logbookServerFilter(logbook: Logbook): LogbookServerFilter {
        return LogbookServerFilter(logbook);
    }*/

}