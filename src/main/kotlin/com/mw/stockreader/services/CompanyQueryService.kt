package com.mw.stockreader.services

import com.mw.stockreader.entities.stocks.Company
import com.mw.stockreader.exceptions.NoMatchingCompanyException
import org.apache.http.client.methods.CloseableHttpResponse

interface CompanyQueryService {
    @Throws(NoMatchingCompanyException::class)
    fun getCompanyByCompanySymbol(companySymbol: String): Company
    fun buildCompanyFromResponse(response: CloseableHttpResponse): Company
}