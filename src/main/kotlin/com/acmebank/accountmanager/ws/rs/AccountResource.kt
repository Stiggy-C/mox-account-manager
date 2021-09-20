package com.acmebank.accountmanager.ws.rs

import com.acmebank.accountmanager.data.domain.Account
import java.util.*
import javax.ws.rs.container.AsyncResponse

interface AccountResource: AbstractMutableEntityResource<Account, UUID> {

    fun findByNumber(number: Long, asyncResponse: AsyncResponse)

    fun transfer(fromAccountNumber: Long, toAccountNumber: Long, amount: Double, asyncResponse: AsyncResponse)
}