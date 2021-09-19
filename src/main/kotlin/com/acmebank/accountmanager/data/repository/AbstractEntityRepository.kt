package com.acmebank.accountmanager.data.repository

import com.acmebank.accountmanager.data.domain.AbstractEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface AbstractEntityRepository<T: AbstractEntity<ID>, ID: Serializable>: JpaRepository<T, ID> {
}