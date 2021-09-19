package com.acmebank.accountmanager.data.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Transient

@Entity
class Account: AbstractMutableEntity<UUID>() {

    @Id
    override var id: UUID? = null

    var balance: Double = 0.0

    var number: Long? = null

    @Transient
    var lastReferenceUUID: UUID? = null

    @PrePersist
    override fun prePersist() {
        super.prePersist()

        id = UUID.randomUUID()
    }
}