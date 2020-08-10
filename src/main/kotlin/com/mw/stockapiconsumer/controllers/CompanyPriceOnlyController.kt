package com.mw.stockapiconsumer.controllers

import com.google.gson.Gson
import com.mw.stockapiconsumer.services.CompanyPriceOnlyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CompanyPriceOnlyController(private val service: CompanyPriceOnlyService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyPriceOnlyController.toString())
    }

    @GetMapping("/company/price-only")
    fun getCompanyPriceOnly(@RequestParam("companySymbol") companySymbol: String): String? {
        logger.info("Calling application to get company stock price for $companySymbol")
        return Gson().toJson(service.getCompanyPriceOnlyByCompanySymbol(companySymbol))
    }
}
