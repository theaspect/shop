package me.blzr.shop.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Category(var name: String) {
    @Id @GeneratedValue var id: Long? = null
    @OneToMany(mappedBy = "category") lateinit var items: Set<Item>
}

