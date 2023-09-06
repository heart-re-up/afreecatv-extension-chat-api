package com.streamhelper.microservices.game.draw.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*

@Configuration
class I18nConfig : WebMvcConfigurer {

    @Bean
    fun localeResolver() = CookieLocaleResolver("lang")
        .apply { setDefaultLocale(Locale.getDefault()) }

    @Bean
    fun localeChangeInterceptor() = LocaleChangeInterceptor()
        .apply { paramName = "lang" }

    @Bean
    @Qualifier("wideshot_transmission_result")
    fun messageSource() = ReloadableResourceBundleMessageSource()
        .apply {
            setBasename("classpath:/i18n/wideshot_transmission_result")
            setDefaultEncoding("UTF-8")
        }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(localeChangeInterceptor())
    }
}