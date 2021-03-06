package me.blzr.shop

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.http.MediaType
import org.springframework.stereotype.Component


@SpringBootApplication

open class ShopApplication @Autowired constructor(
        private val config: Config,
        private val xlsParser: XlsParser) : InitializingBean {
    private val log = LoggerFactory.getLogger(ShopApplication::class.java)

    override fun afterPropertiesSet() {
        if (config.parse) {

            log.info("Cleanup DB")
            xlsParser.cleanup()

            log.info("Populate dataset")
            xlsParser.parse(this.javaClass.classLoader.getResourceAsStream("nomeclature.xls"))

            log.info("Done")
        } else {
            log.debug("Skip loading initial dataset")
        }
    }

    // https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
    @Component
    @ConfigurationProperties(prefix = "app")
    class Config {
        var parse: Boolean = false
    }

    @Bean
    open fun repositoryRestConfigurer(): RepositoryRestConfigurer {
        return object : RepositoryRestConfigurerAdapter() {
            override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration?) {
                config?.defaultMediaType = MediaType.APPLICATION_JSON
            }
        }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(ShopApplication::class.java, *args)
        }
    }


}
