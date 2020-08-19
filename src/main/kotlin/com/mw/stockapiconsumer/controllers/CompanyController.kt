package com.mw.stockapiconsumer.controllers

import com.google.gson.Gson
import com.mw.stockapiconsumer.repositories.CompanyRepository
import com.mw.stockapiconsumer.services.CompanyService
import com.mw.stockapiconsumer.tables.CompanyTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CompanyController(private val service: CompanyService,
                        private val repository: CompanyRepository) {

    // These controllers are primarily for testing during development and will be made redundant in later versions

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyController.toString())
    }

    @GetMapping("/company")
    fun getCompanyInformation(@RequestParam("companySymbol") companySymbol: String): String? {

        logger.info("Calling application to get company information for $companySymbol")

        val company = service.getCompanyByCompanySymbol(companySymbol)
        repository.save(company)

        logger.info("Successfully retrieved company: $company")

        return Gson().toJson(company)
    }
}
