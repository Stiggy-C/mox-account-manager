package com.acmebank.accountmanager.ws.rs

import com.acmebank.accountmanager.data.domain.AbstractMutableEntity
import com.acmebank.accountmanager.service.AbstractMutableEntityService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.json.Json
import jakarta.json.JsonMergePatch
import jakarta.json.JsonValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable
import java.io.StringReader
import javax.persistence.EntityNotFoundException
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.core.Response

abstract class AbstractAbstractMutableEntityResourceImpl<T : AbstractMutableEntity<ID>, ID : Serializable>(
    @Autowired protected val abstractMutableEntityService: AbstractMutableEntityService<T, ID>,
    @Autowired protected open val objectMapper: ObjectMapper
) : AbstractMutableEntityResource<T, ID>, AbstractEntityResourceImpl<T, ID>(abstractMutableEntityService) {


    override fun update(id: ID, jsonMergePatch: JsonMergePatch, asyncResponse: AsyncResponse) {
        scope.launch(Dispatchers.IO) {
            try {
                val persistedEntityOptional = abstractEntityService.retrieve(id)

                if (persistedEntityOptional.isEmpty) {
                    asyncResponse.resume(EntityNotFoundException())
                }

                val persistedEntity = persistedEntityOptional.get()
                val persistedEntityAsJsonValue =
                    Json.createReader(StringReader(objectMapper.writeValueAsString(persistedEntity))).readObject()
                val mergedEntityAsJsonValue = jsonMergePatch.apply(persistedEntityAsJsonValue)
                val mergedEntityAsJsonString = mergedEntityAsJsonValue.asJsonObject().toString()
                val updatedEntity =
                    objectMapper.readerForUpdating(persistedEntity).readValue<T>(mergedEntityAsJsonString)

                abstractMutableEntityService.update(updatedEntity)
            } catch (e: Exception) {
                asyncResponse.resume(e)

                return@launch
            }

            asyncResponse.resume(Response.status(Response.Status.NO_CONTENT).build())
        }
    }
}