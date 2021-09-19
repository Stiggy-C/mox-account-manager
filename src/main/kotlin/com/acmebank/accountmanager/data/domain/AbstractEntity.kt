package com.acmebank.accountmanager.data.domain

import java.io.Serializable
import java.time.OffsetDateTime
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist

@MappedSuperclass
abstract class AbstractEntity<ID: Serializable> {

    @Id
    open var id: ID? = null

    open var createdBy: String? = null

    open var createdDateTime: OffsetDateTime? = null

    @PrePersist
    protected open fun prePersist() {
        createdDateTime = OffsetDateTime.now()
    }
}