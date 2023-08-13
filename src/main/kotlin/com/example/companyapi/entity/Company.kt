package com.example.companyapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Company(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        var name : String,
        var email : String= "",
        var url : String = "",
        var businessId : String = "",
        val address: String = "",
        val phoneNumber: String = "",
)
