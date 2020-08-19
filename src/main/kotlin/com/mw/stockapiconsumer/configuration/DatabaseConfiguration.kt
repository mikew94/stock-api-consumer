package com.mw.stockapiconsumer.configuration

import com.mw.stockapiconsumer.services.impl.CompanyServiceImpl
import org.jetbrains.exposed.sql.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Component
import javax.sql.DataSource


@Component
@ConfigurationProperties(prefix = "database")
@EnableConfigurationProperties
data class DatabaseConfiguration(
        var name: String? = null,
        var user: String? = null,
        var password: String? = null,
        var endpoint: String? = null,
        var port: String? = null
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DatabaseConfiguration.toString())
    }

    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver")
        dataSource.url = "jdbc:mysql://$endpoint:$port/$name"
        dataSource.username = user
        dataSource.password = password
        return dataSource
    }

    @Bean
    fun connect() {
        logger.info("Connecting to ${dataSource()}")
        Database.connect(dataSource())
    }
}