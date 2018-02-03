package me.blzr.shop.repository

import me.blzr.shop.domain.Category
import org.springframework.data.repository.CrudRepository

interface Categories : CrudRepository<Category, Long>
