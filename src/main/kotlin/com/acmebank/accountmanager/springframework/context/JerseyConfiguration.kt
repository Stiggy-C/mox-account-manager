package com.acmebank.accountmanager.springframework.context

import com.acmebank.accountmanager.ws.rs.JsonMergePatchMessageBodyReader
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
// import org.zalando.logbook.jaxrs.LogbookServerFilter
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import javax.ws.rs.ApplicationPath
import javax.ws.rs.Path

@Configuration
@ApplicationPath("/rs")
@Order
class JerseyConfiguration : ResourceConfig() {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Autowired
    lateinit var jacksonJaxbJsonProvider: JacksonJaxbJsonProvider

    /*@Autowired
    lateinit var logbookServerFilter: LogbookServerFilter*/

    @PostConstruct
    protected fun postConstruct() {
        // Providers:
        registerClasses(JsonMergePatchMessageBodyReader::class.java)
        registerInstances(jacksonJaxbJsonProvider)
        // registerInstances(logbookServerFilter)

        // Resources:
        registerInstances(
            applicationContext.getBeansWithAnnotation(Path::class.java).values.stream().collect(Collectors.toSet())
        )
    }
}