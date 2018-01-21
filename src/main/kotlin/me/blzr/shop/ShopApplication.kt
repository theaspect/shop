package me.blzr.shop

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.concurrent.CompletableFuture

@SpringBootApplication
open class ShopApplication {
//    @Bean
//    open fun model(): XlsParser.Model =
//            XlsParser.parse(this.javaClass.getResourceAsStream("nomeclature.xls"))

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val futureModel = CompletableFuture. supplyAsync {
                return@supplyAsync XlsParser.parse(this::class.java.getResourceAsStream("/nomeclature.xls"))
            }

            SpringApplication.run(ShopApplication::class.java, *args)
        }
    }
}
