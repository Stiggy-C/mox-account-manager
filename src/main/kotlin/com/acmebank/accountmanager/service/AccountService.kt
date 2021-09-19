package com.acmebank.accountmanager.service

import com.acmebank.accountmanager.data.domain.Account
import java.util.*

interface AccountService: AbstractMutableEntityService<Account, UUID> {

    fun findByNumber(number: Long): Account?

    fun transfer(from: Account, to: Account, amount: Double): UUID
}