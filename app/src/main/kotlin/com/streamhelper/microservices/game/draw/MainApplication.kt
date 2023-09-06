package com.streamhelper.microservices.game.draw

import com.tinteccnc.util.MyLogger
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import java.util.*


@SpringBootApplication
@ConfigurationPropertiesScan
@EnableMethodSecurity(securedEnabled = true)
class DrawGameApplication : MyLogger {
    @PostConstruct
    fun started() {
        TimeZone.getTimeZone("UTC")
            .also { info("TimeZone has set to $it") }
            .also { TimeZone.setDefault(it) }
    }
}

fun main(args: Array<String>) {
    runApplication<com.streamhelper.microservices.game.draw.DrawGameApplication>(*args)
}