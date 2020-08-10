package com.mw.stockreader.services.impl

import com.mw.stockreader.configuration.ApiConnectionConfiguration
import com.mw.stockreader.configuration.HttpClientProvider
import com.mw.stockreader.entities.CompanyPriceOnly
import com.mw.stockreader.services.CompanyPriceOnlyService
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.apache.http.HttpEntity
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.io.ByteArrayInputStream

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [CompanyPriceOnlyServiceImpl::class])
class CompanyPriceOnlyServiceImplTest {

    @Autowired
    private lateinit var service: CompanyPriceOnlyService

    @MockBean
    private lateinit var httpClientProvider: HttpClientProvider

    @MockBean
    private lateinit var apiConnectionConfiguration: ApiConnectionConfiguration

    @Before
    fun init() {
        Mockito.reset(httpClientProvider)
    }

    @Test
    fun givenValidAAPLCompanySymbolWhenCallingServiceForCompanyPriceOnlyThenPriceOnlyIsReturnedAndStatusCode200() {

        val mockToken = "sandbox"
        val mockUrl = "https://dummyapi"

        val companySymbol = "AAPL"

        val responseBody = priceOnlyResponseBody()

        val byteArrayInputStream = ByteArrayInputStream(responseBody.toByteArray())
        val statusLine = Mockito.mock(StatusLine::class.java)
        val entity = Mockito.mock(HttpEntity::class.java)

        given(entity.content).willReturn(byteArrayInputStream)
        given(statusLine.statusCode).willReturn(200)

        val httpResponse = Mockito.mock(CloseableHttpResponse::class.java)

        given(httpResponse.statusLine).willReturn(statusLine)
        given(httpResponse.entity).willReturn(entity)

        val httpClient = Mockito.mock(CloseableHttpClient::class.java)

        given(httpClient.execute(ArgumentMatchers.any(HttpGet::class.java))).willReturn(httpResponse)
        given(httpClientProvider.client()).willReturn(httpClient)

        whenever(apiConnectionConfiguration.apiToken()).thenReturn(mockToken)
        whenever(apiConnectionConfiguration.apiUrl()).thenReturn(mockUrl)

        val company = service.getCompanyPriceOnlyByCompanySymbol(companySymbol)

        Assert.assertEquals(companySymbol, "AAPL")
        Assert.assertEquals(company, validCompanyPriceOnlyObject())

        val argumentCaptor = ArgumentCaptor.forClass(HttpGet::class.java)
        verify(httpClient, times(1)).execute(argumentCaptor.capture())
    }

    fun priceOnlyResponseBody() =
            """38.04""".trimMargin()

    fun validCompanyPriceOnlyObject() =
            CompanyPriceOnly("AAPL", 38.04)
}
