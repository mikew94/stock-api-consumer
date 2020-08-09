package com.mw.stockreader.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("apiUrlConfig")
class ApiUrlProvider {

    @Bean
    fun apiUrl(): String =
            if (isSandboxEnabled.toBoolean()) {
                apiUrlSandbox
            } else {
                apiUrlProduction
            }

    @Value("\${sandbox.enabled:NOT_SET}")
    private lateinit var isSandboxEnabled: String

    @Value("\${api.url.production:NOT_SET}")
    private lateinit var apiUrlProduction: String

    @Value("\${api.url.sandbox:NOT_SET}")
    private lateinit var apiUrlSandbox: String
}