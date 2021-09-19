package com.acmebank.accountmanager.rs

import com.acmebank.accountmanager.data.domain.AbstractEntity
import com.acmebank.accountmanager.service.AbstractEntityService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable
import javax.persistence.EntityNotFoundException
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.core.Response

abstract class AbstractEntityResourceImpl<T : AbstractEntity<ID>, ID : Serializable>(
    @Autowired protected val abstractEntityService: AbstractEntityService<T, ID>
) : AbstractEntityResource<T, ID> {

    protected val scope = CoroutineScope(Dispatchers.Default)

    override fun create(entity: T, asyncResponse: AsyncResponse) {
        scope.launch {
            try {
                abstractEntityService.create(entity)
            } catch (e: Exception) {
                asyncResponse.resume(e)
            }

            asyncResponse.resume(Response.status(Response.Status.CREATED).entity(entity).build())
        }
    }

    override fun retrieve(id: ID, asyncResponse: AsyncResponse) {
        scope.launch {
            var entity: T? = null
            try {
                entity = abstractEntityService.retrieve(id).orElseThrow { EntityNotFoundException() }
            } catch (e: Exception) {
                asyncResponse.resume(e)
            }

            asyncResponse.resume(Response.status(Response.Status.CREATED).entity(entity).build())
        }
    }

    override fun delete(id: ID, asyncResponse: AsyncResponse) {
        scope.launch {
            try {
                abstractEntityService.delete(id)
            } catch (e: Exception) {
                asyncResponse.resume(e)
            }

            asyncResponse.resume(Response.status(Response.Status.NO_CONTENT).build())
        }
    }
}