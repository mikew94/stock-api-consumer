package com.mw.stockapiconsumer.tables

import org.jetbrains.exposed.dao.IntIdTable

object CompanyTable: IntIdTable("COMPANY") {
    val symbol = varchar("symbol", 50).uniqueIndex()
    val companyName = varchar("company_name", 50)
    val exchange = varchar("company_name", 50)
    val industry = varchar("company_name", 50)
    val website = varchar("company_name", 50)
    val description = varchar("company_name", 50)
    val ceo = varchar("company_name", 50)
    val securityName = varchar("company_name", 50)
    val issueType = varchar("company_name", 50)
    val sector = varchar("company_name", 50)
    val primarySicCode = integer("company_name")
    val employees = integer("company_name")
    val tags = varchar("company_name", 50)
    val address = varchar("company_name", 50)
    val address2 = varchar("company_name", 50)
    val state = varchar("company_name", 50)
    val city = varchar("company_name", 50)
    val zip = varchar("company_name", 50)
    val country = varchar("company_name", 50)
    val phone = varchar("company_name", 50)
}
