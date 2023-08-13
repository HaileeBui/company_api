package com.example.companyapi.repository

import com.example.companyapi.entity.Company
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CompanyRepository: CrudRepository<Company, Int> {

    @Query(value = "SELECT company FROM Company as company")
    fun getAllCompanies() : List<Company>
}