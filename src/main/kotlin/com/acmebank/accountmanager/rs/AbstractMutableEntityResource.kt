package com.acmebank.accountmanager.rs

import com.acmebank.accountmanager.data.domain.AbstractMutableEntity
import jakarta.json.JsonMergePatch
import java.io.Serializable
import javax.ws.rs.container.AsyncResponse

interface AbstractMutableEntityResource<T: AbstractMutableEntity<ID>, ID: Serializable> : AbstractEntityResource<T, ID> {

    fun update(id: ID, jsonMergePatch: JsonMergePatch, asyncResponse: AsyncResponse)
}