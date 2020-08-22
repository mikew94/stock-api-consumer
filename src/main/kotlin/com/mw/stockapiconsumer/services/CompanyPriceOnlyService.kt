package com.mw.stockapiconsumer.services

import com.mw.stockapiconsumer.entities.CompanyPriceOnly
import com.mw.stockapiconsumer.exceptions.NoMatchingCompanyException
import org.apache.http.client.methods.CloseableHttpResponse

interface CompanyPriceOnlyService {
    @Throws(NoMatchingCompanyException::class)
    fun getCompanyPriceOnlyByCompanySymbol(companySymbol: String): CompanyPriceOnly
    fun getUrl(companySymbol: String): String
    fun buildCompanyPriceOnlyFromResponse(response: CloseableHttpResponse, companySymbol: String): CompanyPriceOnly
}
