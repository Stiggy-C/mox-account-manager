package com.acmebank.accountmanager.ws.rs

import jakarta.json.Json
import jakarta.json.JsonMergePatch
import jakarta.json.stream.JsonParser
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.reflect.TypeUtils
import org.glassfish.json.JsonMergePatchImpl
import java.io.InputStream
import java.io.StringReader
import java.lang.reflect.Type
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.Provider

@Provider
@Consumes("application/merge-patch+json")
class JsonMergePatchMessageBodyReader: MessageBodyReader<JsonMergePatch> {

    override fun isReadable(p0: Class<*>?, p1: Type?, p2: Array<out Annotation>?, p3: MediaType?): Boolean {
        return TypeUtils.isAssignable(p0, p1)
    }

    override fun readFrom(
        p0: Class<JsonMergePatch>?,
        p1: Type?,
        p2: Array<out Annotation>?,
        p3: MediaType?,
        p4: MultivaluedMap<String, String>?,
        p5: InputStream?
    ): JsonMergePatch {
        val byteArray = IOUtils.readFully(p5, p5!!.available())
        val jsonString = String(byteArray, Charsets.UTF_8)
        val jsonObject = Json.createReader(StringReader(jsonString)).readObject()

        return Json.createMergePatch(jsonObject)
    }
}