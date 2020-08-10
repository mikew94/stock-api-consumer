package com.mw.stockapiconsumer.services.impl

import com.google.gson.Gson
import com.mw.stockapiconsumer.configuration.ApiConnectionConfiguration
import com.mw.stockapiconsumer.configuration.HttpClientProvider
import com.mw.stockapiconsumer.entities.stockprofiles.Company
import com.mw.stockapiconsumer.exceptions.NoMatchingCompanyException
import com.mw.stockapiconsumer.services.CompanyService
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class CompanyServiceImpl(private val httpClientProvider: HttpClientProvider,
                         private val apiConnectionConfiguration: ApiConnectionConfiguration) : CompanyService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyServiceImpl.toString())
    }

    override fun getCompanyByCompanySymbol(companySymbol: String): Company {

        logger.info("Getting company information for $companySymbol")

        val url = getUrl(companySymbol)

        httpClientProvider.client().use { client ->

            val httpGet = HttpGet(url)

            client.execute(httpGet).use { response ->

                val statusCode = response.statusLine.statusCode

                logger.info("Calling for company information with response status code", "url" to url, "statusCode" to statusCode)

                return when (statusCode) {
                    200 -> {
                        CompanyPriceOnlyServiceImpl.logger.info("Successfully retrieved company information", "response" to response)
                        buildCompanyFromResponse(response)
                    }
                    404 ->
                        throw NoMatchingCompanyException("Company with symbol $companySymbol does not exist")
                    else ->
                        throw Exception("Woops")
                }
            }
        }
    }

    override fun getUrl(companySymbol: String): String {
        val token = apiConnectionConfiguration.apiToken()
        val baseUrl = apiConnectionConfiguration.apiUrl()
        return  "$baseUrl/stock/$companySymbol/company?token=$token"
    }

    override fun buildCompanyFromResponse(response: CloseableHttpResponse): Company {
        val entity = response.entity
        val text = BufferedReader(InputStreamReader(entity.content)).use(BufferedReader::readText)
        EntityUtils.consume(entity)
        return Gson().fromJson(text, Company::class.java)
    }
}
