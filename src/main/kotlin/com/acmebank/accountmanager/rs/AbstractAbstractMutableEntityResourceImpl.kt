package com.acmebank.accountmanager.rs

import com.acmebank.accountmanager.data.domain.AbstractMutableEntity
import com.acmebank.accountmanager.service.AbstractMutableEntityService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.json.JsonMergePatch
import jakarta.json.JsonValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable
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
                val persistedEntityAsJsonValue = objectMapper.convertValue(persistedEntity, JsonValue::class.java)
                val mergedEntityAsJsonValue = jsonMergePatch.apply(persistedEntityAsJsonValue)
                val jsonString = objectMapper.writeValueAsString(mergedEntityAsJsonValue)
                val updatedEntity = objectMapper.readerForUpdating(persistedEntity).readValue<T>(jsonString)

                abstractMutableEntityService.update(updatedEntity)
            } catch (e: Exception) {
                asyncResponse.resume(e)
            }

            asyncResponse.resume(Response.status(Response.Status.NO_CONTENT).build())
        }
    }
}