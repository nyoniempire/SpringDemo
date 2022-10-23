package com.ankh.spring_one.spring.datasource

import com.ankh.spring_one.spring.model.Bank

interface BankDataSource {
    fun getBanks(): Collection<Bank>

    fun getBank(accountNumber: String): Bank

    fun createBank(newBank: Bank): Bank

    fun patchBank(bankToUpdate: Bank) : Bank

    fun deleteBank(accountNumber: String)
}