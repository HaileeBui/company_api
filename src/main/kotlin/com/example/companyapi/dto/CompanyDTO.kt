package com.example.companyapi.dto

data class CompanyDTO(
        val id: Int = -1,
        var name : String = "Default value",
        var email : String= "",
        var url : String = "",
        var businessId : String = "",
        var address: String = "",
        var phoneNumber: String = "",
)
