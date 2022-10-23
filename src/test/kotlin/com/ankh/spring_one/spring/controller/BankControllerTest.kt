package com.ankh.spring_one.spring.controller

import com.ankh.spring_one.spring.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
) {


    private val baseUrl = "/api/banks"

    @Test
    fun `should return all banks`() {
        mockMvc.get("/api/banks")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") { value("1234") }
            }
    }

    @Test
    fun `should return the bank with the given account number`() {
        val accountNumber = "1234"

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.trust") { value(2.0) }
            }
    }

    @Test
    fun `should return Not found if account number doesnt exist`() {
        val non_existant_account_number = "100000"
        mockMvc.get("/api/banks/$non_existant_account_number")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Nested
    @DisplayName("POST /api/banks/")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add new bank`() {
            val newBank = Bank("0987", 3.6, 9)

            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("0987") }
                }
        }

        @Test
        fun `should return BAD REQUEST id bank with account number already exist`() {
            val newBank = Bank("1234", 3.6, 9)

            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Patch {
        @Test
        fun `should update bank with new transaction fee`() {
            val updatedBank = Bank("1234", 4.0, 5)

            mockMvc.patch("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                }


            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andDo { print() }
                .andExpect {
                    content { json(objectMapper.writeValueAsString(updatedBank)) }
                }
        }

        @Test
        fun `should return no such element error when bank with account number does not exist`() {
            val updatedBank = Bank("0765", 4.0, 5)

            mockMvc.patch("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }


    @Nested
    @DisplayName("DELETE api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Delete{

        @Test
        fun `should delete given bank if it exist`(){
            val accountNumber = 1342

            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NotFount if account number does not exist`(){
            val account_number_does_not_exist = "fake"

            mockMvc.delete("$baseUrl/$account_number_does_not_exist")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

}

