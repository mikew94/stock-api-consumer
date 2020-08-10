package com.mw.stockreader.controllers

import com.google.gson.Gson
import com.mw.stockreader.services.CompanyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CompanyController(private val service: CompanyService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyController.toString())
    }

    @GetMapping("/company")
    fun getCompanyInformation(@RequestParam("companySymbol") companySymbol: String): String? {
        logger.info("Calling application to get company information for $companySymbol")
        return Gson().toJson(service.getCompanyByCompanySymbol(companySymbol))
    }
}
