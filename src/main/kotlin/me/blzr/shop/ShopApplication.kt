package me.blzr.shop

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ShopApplication {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(ShopApplication::class.java, *args)
        }
    }
}
