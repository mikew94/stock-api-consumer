package com.mw.stockapiconsumer.dao

import com.mw.stockapiconsumer.tables.CompanyTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID

class CompanyDAO(id: EntityID<Int>): Entity<Int>(id) {
    
    companion object : EntityClass<Int, CompanyDAO>(CompanyTable)

    var symbol by CompanyTable.symbol
    var companyName by CompanyTable.companyName
    var exchange by CompanyTable.exchange
    var industry by CompanyTable.industry
    var website by CompanyTable.website
    var description by CompanyTable.description
    var ceo by CompanyTable.ceo
    var securityName by CompanyTable.securityName
    var issueType by CompanyTable.issueType
    var sector by CompanyTable.sector
    var primarySicCode by CompanyTable.primarySicCode
    var employees by CompanyTable.employees
    var tags by CompanyTable.tags
    var address by CompanyTable.address
    var address2 by CompanyTable.address2
    var state by CompanyTable.state
    var city by CompanyTable.city
    var zip by CompanyTable.zip
    var country by CompanyTable.country
    var phone by CompanyTable.phone
}
