package com.acmebank.accountmanager.service

import com.acmebank.accountmanager.data.domain.AbstractMutableEntity
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

abstract class AbstractAbstractMutableEntityServiceImpl<T : AbstractMutableEntity<ID>, ID : Serializable> :
    AbstractMutableEntityService<T, ID>, AbstractAbstractEntityServiceImpl<T, ID>() {

    @Transactional
    override fun update(entity: T) {
        if (entity.id == null) {
            throw IllegalArgumentException("entity has not been created")
        }

        abstractEntityRepository.save(entity)
    }
}