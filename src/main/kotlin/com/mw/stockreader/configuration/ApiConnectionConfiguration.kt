package com.mw.stockreader.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "api")
@EnableConfigurationProperties
data class ApiConnectionConfiguration(
        var token: String? = null,
        var url: String? = null) {

    @Bean
    fun apiToken(): String? = token

    @Bean
    fun apiUrl(): String? = url
}
