package com.mw.stockapiconsumer.services.impl

import com.mw.stockapiconsumer.configuration.ApiConnectionConfiguration
import com.mw.stockapiconsumer.configuration.HttpClientProvider
import com.mw.stockapiconsumer.entities.CompanyPriceOnly
import com.mw.stockapiconsumer.exceptions.NoMatchingCompanyException
import com.mw.stockapiconsumer.services.CompanyPriceOnlyService
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class CompanyPriceOnlyServiceImpl(private val httpClientProvider: HttpClientProvider,
                                  private val apiConnectionConfiguration: ApiConnectionConfiguration) : CompanyPriceOnlyService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyPriceOnlyServiceImpl.toString())
    }

    override fun getCompanyPriceOnlyByCompanySymbol(companySymbol: String): CompanyPriceOnly {

        logger.info("Getting company stock price for $companySymbol")

        val url = getUrl(companySymbol)

        httpClientProvider.client().use { client ->

            val httpGet = HttpGet(url)

            client.execute(httpGet).use { response ->

                val statusCode = response.statusLine.statusCode

                logger.info("Calling for company stock price with response status code $statusCode", "url" to url, "statusCode" to statusCode)

                return when (statusCode) {
                    200 -> {
                        logger.info("Successfully retrieved company stock price: $response", "response" to response)
                        buildCompanyPriceOnlyFromResponse(response, companySymbol)
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
        return "$baseUrl/stock/$companySymbol/price?token=$token"
    }

    override fun buildCompanyPriceOnlyFromResponse(response: CloseableHttpResponse, companySymbol: String): CompanyPriceOnly {
        val entity = response.entity
        val text = BufferedReader(InputStreamReader(entity.content)).use(BufferedReader::readText)
        EntityUtils.consume(entity)
        return CompanyPriceOnly(companySymbol, text.toDouble())
    }
}
