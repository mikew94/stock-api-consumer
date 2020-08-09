package com.mw.stockreader.services

import com.mw.stockreader.entities.CompanyPriceOnly
import com.mw.stockreader.exceptions.NoMatchingCompanyException
import org.apache.http.client.methods.CloseableHttpResponse

interface CompanyPriceOnlyService {
    @Throws(NoMatchingCompanyException::class)
    fun getCompanyPriceOnlyByCompanySymbol(companySymbol: String): CompanyPriceOnly
    fun getUrl(companySymbol: String): String
    fun buildCompanyPriceOnlyFromResponse(response: CloseableHttpResponse, companySymbol: String): CompanyPriceOnly
}