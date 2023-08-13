package com.example.companyapi.service

import com.example.companyapi.dto.CompanyDTO

interface CompanyService {
    fun createCompany(companyDTO: CompanyDTO) : CompanyDTO

    fun getCompanies() : List<CompanyDTO>

    fun getCompany(int: Int): CompanyDTO

    fun updateCompany(companyDTO: CompanyDTO): CompanyDTO

    fun deleteCompany(id: Int)
}