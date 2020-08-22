package com.mw.stockapiconsumer.entities

import com.google.gson.annotations.SerializedName

data class Company(val symbol: String,
                   val companyName: String,
                   val exchange: String,
                   val industry: String,
                   val website: String,
                   val description: String,
                   @SerializedName("CEO") val ceo: String,
                   val securityName: String,
                   val issueType: String,
                   val sector: String,
                   val primarySicCode: Int,
                   val employees: Int,
                   val tags: List<String>,
                   val address: String,
                   val address2: String,
                   val state: String,
                   val city: String,
                   val zip: String,
                   val country: String,
                   val phone: String)
