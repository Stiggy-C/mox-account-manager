package com.acmebank.accountmanager.service

import com.acmebank.accountmanager.data.domain.AbstractEntity
import com.acmebank.accountmanager.data.repository.AbstractEntityRepository
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.util.*
import javax.inject.Inject

abstract class AbstractAbstractEntityServiceImpl<T : AbstractEntity<ID>, ID : Serializable> :
    AbstractEntityService<T, ID> {

    @Inject
    protected lateinit var abstractEntityRepository: AbstractEntityRepository<T, ID>

    @Transactional
    override fun create(entity: T): T {
        return abstractEntityRepository.save(entity)
    }

    @Transactional(readOnly = true)
    override fun retrieve(id: ID): Optional<T> {
        return abstractEntityRepository.findById(id)
    }

    @Transactional
    override fun delete(entity: T) {
        abstractEntityRepository.delete(entity)
    }

    @Transactional
    override fun delete(id: ID) {
        abstractEntityRepository.deleteById(id)
    }
}