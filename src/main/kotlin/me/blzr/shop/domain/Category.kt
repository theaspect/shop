package me.blzr.shop.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Category(
        @Id @GeneratedValue val id: Long? = null,
        val name: String)

