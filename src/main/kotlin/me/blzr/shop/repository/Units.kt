package me.blzr.shop.repository

import me.blzr.shop.domain.Unit
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource interface Units : ReadOnlyCrudRepository<Unit, Long>
