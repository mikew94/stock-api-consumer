package com.mw.stockapiconsumer.configuration

import org.apache.http.impl.client.CloseableHttpClient

interface HttpClientProvider {
    fun client(): CloseableHttpClient
}