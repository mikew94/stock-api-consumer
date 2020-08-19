package com.mw.stockapiconsumer.repositories

import com.mw.stockapiconsumer.dao.CompanyDAO
import com.mw.stockapiconsumer.entities.Company
import com.mw.stockapiconsumer.services.impl.CompanyServiceImpl
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class CompanyRepository {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CompanyServiceImpl.toString())
    }

    fun save(company: Company) {
        transaction {
            logger.info("Saving company to database", "company" to company.toString())
            CompanyDAO.new {
                symbol = company.symbol
                companyName = company.companyName
                exchange = company.exchange
                industry = company.industry
                website = company.website
                description = company.description
                ceo = company.ceo
                securityName = company.securityName
                issueType = company.issueType
                sector = company.sector
                primarySicCode = company.primarySicCode
                employees = company.employees
                tags = company.tags.toString()
                address = company.address
                address2 = company.address2
                state = company.state
                city = company.city
                zip = company.zip
                country = company.country
                phone = company.phone
            }
        }
    }
}
