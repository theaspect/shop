package me.blzr.shop.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.rest.core.annotation.RestResource
import java.io.Serializable

@NoRepositoryBean
interface ReadOnlyCrudRepository<T, ID : Serializable?>: CrudRepository<T, ID> {
    @RestResource(exported = false) override fun <S : T> save(entity: S): S
    @RestResource(exported = false) override fun <S : T> save(entities: MutableIterable<S>?): MutableIterable<S>

    @RestResource(exported = false) override fun deleteAll()
    @RestResource(exported = false) override fun delete(id: ID)
    @RestResource(exported = false) override fun delete(entity: T)
    @RestResource(exported = false) override fun delete(entities: MutableIterable<T>?)
}
