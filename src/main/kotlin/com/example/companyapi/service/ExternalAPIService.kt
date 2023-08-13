package com.example.companyapi.service

import com.example.companyapi.entity.CompanyInfo
import com.example.companyapi.entity.Data
import com.example.companyapi.utils.exceptions.CompanyException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().build()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}

@Service
class ExternalAPIService {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    fun fetchData(id: String): CompanyInfo {
        val restTemplate = RestTemplate()

        val response: ResponseEntity<JsonNode> = restTemplate.getForEntity("https://www.kauppalehti.fi/company-api/basic-info/$id", JsonNode::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            val companyInfo = response.body
            val address = companyInfo?.get("streetAddress")?.get("street")
            val phone = companyInfo?.get("phone")

            val addressData = objectMapper.treeToValue(address, Data::class.java)
            val phoneData = objectMapper.treeToValue(phone, Data::class.java)

            CompanyInfo(addressData, phoneData)
        } else {
            CompanyInfo()
        }
    }
}