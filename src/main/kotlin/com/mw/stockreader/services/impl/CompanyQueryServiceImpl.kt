package com.mw.stockreader.services.impl

import com.google.gson.Gson
import com.mw.stockreader.configuration.ApiTokenProvider
import com.mw.stockreader.configuration.ApiUrlProvider
import com.mw.stockreader.configuration.HttpClientProvider
import com.mw.stockreader.entities.stocks.Company
import com.mw.stockreader.exceptions.NoMatchingCompanyException
import com.mw.stockreader.services.CompanyQueryService
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class CompanyQueryServiceImpl(private val httpClientProvider: HttpClientProvider,
                              private val apiTokenProvider: ApiTokenProvider,
                              private val apiUrlProvider: ApiUrlProvider) : CompanyQueryService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyQueryServiceImpl.toString())
    }

    override fun getCompanyByCompanySymbol(companySymbol: String): Company {

        logger.info("Getting company information for $companySymbol")

        val token = apiTokenProvider.apiToken()
        val baseUrl = apiUrlProvider.apiUrl()
        val url = "$baseUrl/stock/$companySymbol/company?token=$token"

        httpClientProvider.client().use { client ->
            val httpGet = HttpGet(url)

            println(httpGet)
            client.execute(httpGet).use { response ->
                val statusCode = response.statusLine.statusCode

                logger.info("Calling for company information with response status code", "url" to url, "statusCode" to statusCode)

                return when (statusCode) {
                    200 ->
                        buildCompanyFromResponse(response)
                    404 ->
                        throw NoMatchingCompanyException("Company with symbol $companySymbol does not exist")
                    else ->
                        throw Exception("Woops")
                }
            }
        }
    }

    override fun buildCompanyFromResponse(response: CloseableHttpResponse): Company {
        val entity = response.entity
        val text = BufferedReader(InputStreamReader(entity.content)).use(BufferedReader::readText)
        EntityUtils.consume(entity)
        return Gson().fromJson(text, Company::class.java)
    }
}