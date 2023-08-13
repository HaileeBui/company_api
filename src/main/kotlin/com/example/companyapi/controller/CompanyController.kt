package com.example.companyapi.controller

import com.example.companyapi.dto.CompanyDTO
import com.example.companyapi.service.CompanyService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.http.HttpResponse.ResponseInfo

@RestController
@RequestMapping("/company")
class CompanyController(private val companyService: CompanyService) {

    @PostMapping
    fun createCompany(@RequestBody companyDTO:CompanyDTO): ResponseEntity<CompanyDTO>{
        return ResponseEntity(companyService.createCompany(companyDTO), HttpStatus.CREATED)
    }

    @GetMapping
    fun getCompanies(): ResponseEntity<List<CompanyDTO>> =
            ResponseEntity.ok(companyService.getCompanies())

    @GetMapping("/{id}")
    fun getCompany(@PathVariable id: Int) =
            ResponseEntity.ok(companyService.getCompany(id))

    @PutMapping
    fun updateCompany(@RequestBody companyDTO: CompanyDTO) : ResponseEntity<CompanyDTO> =
            ResponseEntity.ok(companyService.updateCompany(companyDTO))

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id : Int) : ResponseEntity<Unit> =
            ResponseEntity(companyService.deleteCompany(id),HttpStatus.NO_CONTENT)

}