package com.ankh.spring_one.spring.datasource.mock

import com.ankh.spring_one.spring.datasource.BankDataSource
import com.ankh.spring_one.spring.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockDataSource() : BankDataSource {

    private val banks = mutableListOf(
        Bank("1234", 2.0, 1),
        Bank("1342", 3.0, 2),
        Bank("0930", 4.0, 3)
    )

    override fun getBanks(): Collection<Bank> {
        return banks
    }

    override fun getBank(accountNumber: String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with such account number")
    }

    override fun createBank(newBank: Bank): Bank {
        if (banks.any { it.accountNumber == newBank.accountNumber })
            throw IllegalArgumentException("Bank with account number ${newBank.accountNumber} already exist")
        banks.add(newBank)
        return newBank
    }

    override fun patchBank(bankToUpdate: Bank): Bank {
        val currentBank = banks.firstOrNull() { it.accountNumber == bankToUpdate.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with such account number")

        banks.remove(currentBank)
        banks.add(bankToUpdate)

        return bankToUpdate
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with such account number")

        banks.remove(currentBank)
    }
}