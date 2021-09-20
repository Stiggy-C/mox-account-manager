package com.acmebank.accountmanager.ws.rs

import com.acmebank.accountmanager.data.domain.Account
import com.acmebank.accountmanager.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.json.JsonMergePatch
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Service
@Path("/accounts")
class AccountResourceImpl(
    @Autowired val accountService: AccountService,
    @Autowired override val objectMapper: ObjectMapper,
    @Autowired val transactionTemplate: TransactionTemplate
) : AccountResource, AbstractAbstractMutableEntityResourceImpl<Account, UUID>(accountService, objectMapper) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    override fun create(entity: Account, @Suspended asyncResponse: AsyncResponse) {
        super.create(entity, asyncResponse)
    }

    @GET
    @Path("/{id}")
    override fun retrieve(@PathParam("id") id: UUID, @Suspended asyncResponse: AsyncResponse) {
        asyncResponse.resume(Response.status(Response.Status.NOT_IMPLEMENTED).build())
    }

    @DELETE
    @Path("/{id}")
    override fun delete(@PathParam("id")  id: UUID, @Suspended asyncResponse: AsyncResponse) {
        asyncResponse.resume(Response.status(Response.Status.NOT_IMPLEMENTED).build())
    }

    @PATCH
    @Path("/{id}")
    @Consumes("application/merge-patch+json")
    @Produces(MediaType.APPLICATION_JSON)
    override fun update(@PathParam("id") id: UUID, jsonMergePatch: JsonMergePatch, @Suspended asyncResponse: AsyncResponse) {
        super.update(id, jsonMergePatch, asyncResponse)
    }

    @GET
    @Path("/number/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    override fun findByNumber(@PathParam("number") number: Long, @Suspended asyncResponse: AsyncResponse) {
        scope.launch {
            var account: Account? = null
            try {
                account = accountService.findByNumber(number)
            } catch (e: Exception) {
                asyncResponse.resume(e)

                return@launch
            }

            if (account == null) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build())
            } else {
                asyncResponse.resume(Response.ok(account).build())
            }
        }
    }

    @POST
    @Path("/from/{fromAccountNumber}/to/{toAccountNumber}/amount/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    override fun transfer(
        @PathParam("fromAccountNumber") fromAccountNumber: Long,
        @PathParam("toAccountNumber") toAccountNumber: Long,
        @PathParam("amount") amount: Double,
        @Suspended asyncResponse: AsyncResponse
    ) {
        scope.launch {
            val referenceUUID = transactionTemplate.execute<UUID> {
                val fromAccount = accountService.findByNumber(fromAccountNumber)

                if (Objects.isNull(fromAccount)) {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build())

                    throw EntityNotFoundException()
                }

                val toAccount = accountService.findByNumber(toAccountNumber)

                if (Objects.isNull(toAccount)) {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build())

                    throw EntityNotFoundException()
                }

                val referenceUUID: UUID
                try {
                    referenceUUID = accountService.transfer(fromAccount!!, toAccount!!, amount)
                } catch (e: Exception) {
                    asyncResponse.resume(e)

                    throw e
                }

                return@execute referenceUUID
            }

            assert(referenceUUID != null)

            asyncResponse.resume(Response.noContent().header("X-Reference-UUID", referenceUUID).build())
        }
    }
}