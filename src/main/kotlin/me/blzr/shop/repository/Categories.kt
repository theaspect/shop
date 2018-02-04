package me.blzr.shop.repository

import me.blzr.shop.domain.Category
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource interface Categories : ReadOnlyCrudRepository<Category, Long>
