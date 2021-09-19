package com.acmebank.accountmanager.service

import com.acmebank.accountmanager.data.domain.AbstractMutableEntity
import java.io.Serializable

interface AbstractMutableEntityService<T: AbstractMutableEntity<ID>, ID: Serializable>: AbstractEntityService<T, ID> {

    fun update(entity: T)
}