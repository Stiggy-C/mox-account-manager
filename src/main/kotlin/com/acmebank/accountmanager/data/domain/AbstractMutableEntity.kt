package com.acmebank.accountmanager.data.domain

import java.io.Serializable
import java.time.OffsetDateTime
import javax.persistence.MappedSuperclass
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class AbstractMutableEntity<ID: Serializable> : AbstractEntity<ID>() {

    open var updatedBy: String? = null

    open var updatedDateTime: OffsetDateTime? = null

    @PreUpdate
    protected open fun preUpdate() {
        updatedDateTime = OffsetDateTime.now()
    }
}