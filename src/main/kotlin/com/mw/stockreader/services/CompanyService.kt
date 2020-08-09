package com.mw.stockreader.services

import com.mw.stockreader.entities.stockprofiles.Company
import com.mw.stockreader.exceptions.NoMatchingCompanyException
import org.apache.http.client.methods.CloseableHttpResponse

interface CompanyService {
    @Throws(NoMatchingCompanyException::class)
    fun getCompanyByCompanySymbol(companySymbol: String): Company
    fun buildCompanyFromResponse(response: CloseableHttpResponse): Company
    fun getUrl(companySymbol: String): String
}