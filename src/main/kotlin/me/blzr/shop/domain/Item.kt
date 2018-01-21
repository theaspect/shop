package me.blzr.shop.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Item(
        @Id @GeneratedValue val id: Long? = null,
        @ManyToOne val supplier: Supplier,
        @ManyToOne val category: Category,
        val group: String,
        val name: String,
        val price: BigDecimal,
        val pack: BigDecimal,
        @ManyToOne val unit: Unit)
