package me.blzr.shop.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Unit(
        @Id @GeneratedValue val id: Long? = null,
        val name: String)
