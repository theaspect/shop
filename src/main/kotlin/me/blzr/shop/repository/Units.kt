package me.blzr.shop.repository

import me.blzr.shop.domain.Unit
import org.springframework.data.repository.CrudRepository

interface Units : CrudRepository<Unit, Long>
