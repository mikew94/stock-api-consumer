package com.mw.stockapiconsumer.services

import com.mw.stockapiconsumer.entities.Company
import com.mw.stockapiconsumer.exceptions.NoMatchingCompanyException
import org.apache.http.client.methods.CloseableHttpResponse

interface CompanyService {
    @Throws(NoMatchingCompanyException::class)
    fun getCompanyByCompanySymbol(companySymbol: String): Company
    fun buildCompanyFromResponse(response: CloseableHttpResponse): Company
    fun getUrl(companySymbol: String): String
}