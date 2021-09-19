package com.acmebank.accountmanager.service

import com.acmebank.accountmanager.data.domain.Account
import com.acmebank.accountmanager.data.repository.AccountRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.inject.Named

@Named
open class AccountServiceImpl : AccountService, AbstractAbstractMutableEntityServiceImpl<Account, UUID>() {

    companion object {

        private val LOG: Logger = LoggerFactory.getLogger(AccountService::class.java)

    }

    @Transactional(readOnly = true)
    override fun findByNumber(number: Long): Account? {
        return (abstractEntityRepository as AccountRepository).findByNumber(number)
    }

    @Transactional
    override fun transfer(from: Account, to: Account, amount: Double): UUID {
        if (from.balance.compareTo(amount) == -1) {
            LOG.warn("Account, {}, has insufficient funds to transfer \${} to account, {}", from.id, amount, to.id)

            throw IllegalArgumentException("Insufficient funds")
        }

        val referenceUUID = UUID.randomUUID()

        if (from.number == to.number) {
            return referenceUUID
        }

        from.balance = from.balance - amount
        to.balance = to.balance + amount

        (abstractEntityRepository as AccountRepository).saveAll(arrayListOf(from, to))

        return referenceUUID
    }
}