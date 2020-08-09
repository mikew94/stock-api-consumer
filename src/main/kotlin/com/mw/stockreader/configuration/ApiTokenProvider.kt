package com.mw.stockreader.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("apiTokenConfig")
class ApiTokenProvider {

    @Bean
    fun apiToken(): String =
            if (isSandboxEnabled.toBoolean()) {
                apiTokenSandbox
            } else {
                apiTokenProduction
            }

    @Value("\${sandbox.enabled:NOT_SET}")
    private lateinit var isSandboxEnabled: String

    @Value("\${api.token.production:NOT_SET}")
    private lateinit var apiTokenProduction: String

    @Value("\${api.token.sandbox:NOT_SET}")
    private lateinit var apiTokenSandbox: String
}