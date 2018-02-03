package me.blzr.shop.repository

import me.blzr.shop.domain.Item
import org.springframework.data.repository.CrudRepository

interface Items : CrudRepository<Item, Long>
