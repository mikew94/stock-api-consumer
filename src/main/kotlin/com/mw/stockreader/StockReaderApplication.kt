package com.mw.stockreader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class StockReaderApplication

fun main(args: Array<String>) {
    runApplication<StockReaderApplication>(*args)
}
