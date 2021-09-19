package com.acmebank.accountmanager.service

import com.acmebank.accountmanager.data.domain.AbstractEntity
import java.io.Serializable
import java.util.*

interface AbstractEntityService<T: AbstractEntity<ID>, ID: Serializable> {

    fun create(entity: T): T

    fun retrieve(id: ID): Optional<T>

    fun delete(entity: T)

    fun delete(id: ID)
}