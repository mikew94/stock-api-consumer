package com.mw.stockreader.entities.stocks

import com.google.gson.annotations.SerializedName

data class Company(private val symbol: String,
                   private val companyName: String,
                   private val exchange: String,
                   private val industry: String,
                   private val website: String,
                   private val description: String,
                   @SerializedName("CEO") private val ceo: String,
                   private val securityName: String,
                   private val issueType: String,
                   private val sector: String,
                   private val primarySicCode: Int,
                   private val employees: Int,
                   private val tags: List<String>,
                   private val address: String,
                   private val address2: String,
                   private val state: String,
                   private val city: String,
                   private val zip: String,
                   private val country: String,
                   private val phone: String)