package com.example.companyapi.service

import com.example.companyapi.dto.CompanyDTO
import com.example.companyapi.entity.Data
import com.example.companyapi.repository.CompanyRepository
import com.example.companyapi.utils.mappers.CompanyMapper
import com.example.companyapi.utils.exceptions.CompanyException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CompanyServiceImpl(
        private val companyRepository: CompanyRepository,
        private val companyMapper: CompanyMapper,
        @Autowired private val externalAPIService: ExternalAPIService,
        ): CompanyService {

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    fun getPhoneFormat(email: String): String {
        return "+358${email.drop(1)}"
    }

    override fun createCompany(companyDTO: CompanyDTO): CompanyDTO {
        if (companyDTO.id != -1)
           throw CompanyException("Id must be null")
        if (companyDTO.name == "Default value")
            throw CompanyException("Name is compulsory")
        if(companyDTO.email.isNotEmpty() && !isValidEmail(companyDTO.email))
            throw CompanyException("Email is not correct")
        if(companyDTO.address.isNotEmpty() || companyDTO.phoneNumber.isNotEmpty())
            throw CompanyException("Can not input address and phone number")
        if(companyDTO.businessId.isNotEmpty()) {
            val companyInfo = externalAPIService.fetchData(companyDTO.businessId)
            if(companyInfo.streetAddress != Data() && companyInfo.phone != Data() ) {
                companyDTO.address = companyInfo.streetAddress.value
                companyDTO.phoneNumber = getPhoneFormat(companyInfo.phone.value)
            } else {
                throw CompanyException("Incorrect business id")
            }
        }
        val company = companyRepository.save(companyMapper.toEntity(companyDTO))
        return companyMapper.fromEntity(company)
    }

    override fun getCompanies(): List<CompanyDTO> {
         val companies = companyRepository.getAllCompanies()

        //if (companies.isEmpty())
        //   throw CompanyException("List of movies is empty")

        return companies.map {
            companyMapper.fromEntity(it)
        }
    }

    override fun getCompany(id: Int): CompanyDTO {
        val returnedCompany = companyRepository.findById(id)
        val company = returnedCompany.orElseThrow{ CompanyException("There is no company with id $id.")}
        return companyMapper.fromEntity(company)
    }

    override fun updateCompany(companyDTO: CompanyDTO): CompanyDTO {
        val exists = companyRepository.existsById(companyDTO.id)

        if(!exists)
            throw CompanyException("There is no company with id ${companyDTO.id}")

        companyRepository.save(companyMapper.toEntity(companyDTO))
        return companyDTO;
    }

    override fun deleteCompany(id: Int) {
        val exists = companyRepository.existsById(id)

        if(!exists)
            throw CompanyException("There is no company with id $id")

        companyRepository.deleteById(id)
    }

}