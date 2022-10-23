package com.ankh.spring_one.spring.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockDataSourceTest {

    private val mockDataSource: MockDataSource = MockDataSource()

    @Test
    fun `should provide a collection of Banks`() {
        val banks = mockDataSource.getBanks()

        assertThat(banks).isNotEmpty
    }

    @Test
    fun `should provide some mock data`() {
        val banks = mockDataSource.getBanks()

        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).allMatch { it.transactionFee != 0 }
    }
}