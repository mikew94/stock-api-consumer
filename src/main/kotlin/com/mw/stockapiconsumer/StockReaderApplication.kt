package com.mw.stockapiconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockReaderApplication

fun main(args: Array<String>) {
    runApplication<StockReaderApplication>(*args)
}
