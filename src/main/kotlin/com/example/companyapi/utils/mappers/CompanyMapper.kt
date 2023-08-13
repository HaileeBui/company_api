package com.example.companyapi.utils.mappers

import com.example.companyapi.dto.CompanyDTO
import com.example.companyapi.entity.Company
import org.springframework.stereotype.Service

@Service
class CompanyMapper: Mapper<CompanyDTO, Company> {
    override fun fromEntity(entity: Company): CompanyDTO  = CompanyDTO(
            entity.id,
            entity.name,
            entity.email,
            entity.url,
            entity.businessId,
            entity.address,
            entity.phoneNumber,
    )

    override fun toEntity(domain: CompanyDTO): Company = Company(
            domain.id,
            domain.name,
            domain.email,
            domain.url,
            domain.businessId,
            domain.address,
            domain.phoneNumber,
    )
}