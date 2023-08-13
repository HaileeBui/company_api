package com.example.companyapi.entity

import java.util.Date

data class CompanyInfo @JvmOverloads constructor(
        val streetAddress: Data = Data(),
        val phone: Data = Data(),
)

data class Data(
        val value: String = "",
        val source: String = "",
        val startDate: String = "",
        val endDate: String = "",
)


