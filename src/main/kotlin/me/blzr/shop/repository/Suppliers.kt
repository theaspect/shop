package me.blzr.shop.repository

import me.blzr.shop.domain.Supplier
import org.springframework.data.repository.CrudRepository

interface Suppliers : CrudRepository<Supplier, Long>
