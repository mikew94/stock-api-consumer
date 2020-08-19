package com.mw.stockapiconsumer.services.impl

import com.mw.stockapiconsumer.configuration.ApiConnectionConfiguration
import com.mw.stockapiconsumer.configuration.HttpClientProvider
import com.mw.stockapiconsumer.entities.Company
import com.mw.stockapiconsumer.exceptions.NoMatchingCompanyException
import com.mw.stockapiconsumer.services.CompanyService
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.fail
import org.apache.http.HttpEntity
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.io.ByteArrayInputStream

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [CompanyServiceImpl::class])
class CompanyServiceImplTest {

    @Autowired
    private lateinit var service: CompanyService

    @MockBean
    private lateinit var httpClientProvider: HttpClientProvider

    @MockBean
    private lateinit var apiConnectionConfiguration: ApiConnectionConfiguration

    @Before
    fun init() {
        reset(httpClientProvider)
    }

    @Test
    fun givenValidAAPLCompanySymbolWhenCallingServiceForCompanyInformationThenCompanyInformationIsReturnedWithStatusCode200() {

        val mockToken = "sandbox"
        val mockUrl = "https://dummyapi"

        val companySymbol = "AAPL"

        val responseBody = companyInformationResponseBody()

        val byteArrayInputStream = ByteArrayInputStream(responseBody.toByteArray())
        val statusLine = mock(StatusLine::class.java)
        val entity = mock(HttpEntity::class.java)

        given(entity.content).willReturn(byteArrayInputStream)
        given(statusLine.statusCode).willReturn(200)

        val httpResponse = mock(CloseableHttpResponse::class.java)

        given(httpResponse.statusLine).willReturn(statusLine)
        given(httpResponse.entity).willReturn(entity)

        val httpClient = mock(CloseableHttpClient::class.java)

        given(httpClient.execute(any(HttpGet::class.java))).willReturn(httpResponse)
        given(httpClientProvider.client()).willReturn(httpClient)

        whenever(apiConnectionConfiguration.apiToken()).thenReturn(mockToken)
        whenever(apiConnectionConfiguration.apiUrl()).thenReturn(mockUrl)

        val company = service.getCompanyByCompanySymbol(companySymbol)

        assertEquals(companySymbol, "AAPL")
        assertEquals(company, validCompanyObject())

        val argumentCaptor = ArgumentCaptor.forClass(HttpGet::class.java)
        verify(httpClient, times(1)).execute(argumentCaptor.capture())
    }

    @Test
    fun givenInvalidCompanySymbolWhenCallingServiceForCompanyInformationThenServiceWillThrowNoMatchingCompanyException() {

        val companySymbol = "invalid"

        val mockToken = "sandbox"
        val mockUrl = "https://dummyapi"

        val statusLine = mock(StatusLine::class.java)

        given(statusLine.statusCode).willReturn(404)

        val httpResponse = mock(CloseableHttpResponse::class.java)

        given(httpResponse.statusLine).willReturn(statusLine)

        val httpClient = mock(CloseableHttpClient::class.java)

        given(httpClient.execute(any(HttpGet::class.java))).willReturn(httpResponse)
        given(httpClientProvider.client()).willReturn(httpClient)

        whenever(apiConnectionConfiguration.apiToken()).thenReturn(mockToken)
        whenever(apiConnectionConfiguration.apiUrl()).thenReturn(mockUrl)

        try {
            service.getCompanyByCompanySymbol(companySymbol)
            fail("Expected a NoMatchingCompanyException")
        } catch (e: NoMatchingCompanyException) {
            val argumentCaptor = ArgumentCaptor.forClass(HttpGet::class.java)
            verify(httpClient, times(1)).execute(argumentCaptor.capture())
        }
    }

    fun validCompanyObject() =
            Company("AAPL", "Apple, Inc.", "AQADSN", "industry", "apple.com",
                    "description", "odl Ctahmo TnyioDok", "lpeIp.c An", "cs",
                    "sector", 3681, 142514, listOf("tag1", "tag2"), "W lOa epnPaAprye k",
                    "awdddd", "CA", "epCuirton", "8901542-03", "US", "phone")

    fun companyInformationResponseBody() =
            """
            |{
            |   "symbol": "AAPL",
            |   "companyName": "Apple, Inc.",
            |   "exchange": "AQADSN",
            |   "industry": "industry",
            |   "website": "apple.com",
            |   "description": "description",
            |   "CEO": "odl Ctahmo TnyioDok",
            |   "securityName": "lpeIp.c An",
            |   "issueType": "cs",
            |   "sector": "sector",
            |   "primarySicCode": 3681,
            |   "employees": 142514,
            |   "tags": [
            |       "tag1",
            |       "tag2"
            |   ],
            |   "address": "W lOa epnPaAprye k",
            |   "address2": "awdddd",
            |   "state": "CA",
            |   "city": "epCuirton",
            |   "zip": "8901542-03",
            |   "country": "US",
            |   "phone": "phone"
            |}
        """.trimMargin()
}
