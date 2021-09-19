package com.acmebank.accountmanager.data.repository

import com.acmebank.accountmanager.data.domain.Account
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: AbstractEntityRepository<Account, UUID> {

    fun findByNumber(number: Long): Account?
}