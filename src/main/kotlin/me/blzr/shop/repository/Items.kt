package me.blzr.shop.repository

import me.blzr.shop.domain.Item
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource interface Items : ReadOnlyCrudRepository<Item, Long>
