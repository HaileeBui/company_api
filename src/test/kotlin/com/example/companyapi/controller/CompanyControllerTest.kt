package com.example.companyapi.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
internal class CompanyControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {

  val baseUrl = "/company"

  @Nested
  @DisplayName("getCompanies()")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class GetCompanies {

    @Test
    fun `should return all companies`() {
      val newCompanyJson1 = """
         {
             "name": "Qvik"
         }
    """
      mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson1
      }

      val newCompanyJson2 = """
         {
             "name": "ABC OY"
         }
    """
      mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson2
      }

      mockMvc.get("/company")
          .andDo { print() }
          .andExpect { status { isOk() } }
    }

    @Test
    fun `should return empty if no companies`() {
      mockMvc.get("/company")
          .andDo { print() }
          .andExpect { status { isOk() } }
    }
  }


  @Nested
  @DisplayName("getCompany()")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class GetCompany {

    @Test
    fun `should return company with given id`() {
      val newCompanyJson = """
         {
             "name": "Qvik"
         }
    """
      mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson
      }

      mockMvc.get("/company/1")
          .andDo { print() }
          .andExpect { status { isOk() } }
    }

    @Test
    fun `should return not found with wrong given id`() {
      mockMvc.get("/company/3")
          .andDo { print() }
          .andExpect { status { isNotFound() } }
    }
  }

  @Nested
  @DisplayName("createCompany()")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class CreateCompany {

    @Test
    fun `should add the new company`() {

      val newCompanyJson = """
         {
             "name": "Qvik"
         }
    """
      val returnedCompanyJson = """
        {
            "id": 1,
            "name": "Qvik",
            "email": "",
            "url": "",
            "businessId": "",
            "address": "",
            "phoneNumber": ""
        }
    """
      val performPost = mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson
      }

      performPost
          .andDo { print() }
          .andExpect {
            status { isCreated() }
            content {
              contentType(MediaType.APPLICATION_JSON)
              json(returnedCompanyJson)
            }
          }

      mockMvc.get("$baseUrl/1")
          .andExpect { content { json(returnedCompanyJson) } }
    }

    @Test
    fun `should return BAD REQUEST if json contain id, address or phone number`() {
      // given
      val newCompanyJson1 = """
        {
            "id" : 1,
            "name": "Qvik",
            "email": "qvik.fi",
        }
    """
      val newCompanyJson2 = """
        {
            "id" : 2,
            "name": "Qvik",
            "email": "qvik.fi",
             "address": "adc",
        }
    """
      val newCompanyJson3 = """
        {
            "id" : 3,
            "name": "Qvik",
            "email": "qvik.fi",
            "phoneNumber": "1234534566"
        }
    """

      // test input with id
      val performPost1 = mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson1
      }

      performPost1
          .andDo { print() }
          .andExpect { status { isBadRequest() } }

      // test input with address
      val performPost2 = mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson2
      }

      performPost2
          .andDo { print() }
          .andExpect { status { isBadRequest() } }

      // test input with phone number
      val performPost3 = mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson3
      }

      performPost3
          .andDo { print() }
          .andExpect { status { isBadRequest() } }
    }

    @Test
    fun `should return address and phone number when provide business id`() {

      val newCompanyJson = """
         {
           "name": "Qvik",
           "businessId": "2233151-3",
         }
    """
      val returnedCompanyJson = """
        { 
           "id": 1,
           "name": "Qvik",
           "email": "",
           "url": "",
           "businessId": "2233151-3",
           "address": "Urho Kekkosen katu 5 C",
           "phoneNumber": "+358207354355",
        }
    """

      val performPost = mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson
      }

      performPost
          .andDo { print() }
          .andExpect {
            status { isCreated() }
            content {
              contentType(MediaType.APPLICATION_JSON)
              json(returnedCompanyJson)
            }
          }
    }
  }

  @Nested
  @DisplayName("deleteCompany()")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class DeleteCompany {

    @Test
    @DirtiesContext
    fun `should delete the bank with the given id`() {

      val newCompanyJson = """
         {
             "name": "Qvik"
         }
    """

      mockMvc.post(baseUrl) {
        contentType = MediaType.APPLICATION_JSON
        content = newCompanyJson
      }

      mockMvc.delete("$baseUrl/1")
          .andDo { print() }
          .andExpect {
            status { isNoContent() }
          }

      mockMvc.get("$baseUrl/1")
          .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return NOT FOUND if no bank with given account number exists`() {
      mockMvc.delete("/company/1")
          .andDo { print() }
          .andExpect { status { isNotFound() } }
    }
  }
}