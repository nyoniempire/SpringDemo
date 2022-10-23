package com.ankh.spring_one.spring.service

import com.ankh.spring_one.spring.datasource.BankDataSource
import com.ankh.spring_one.spring.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.getBanks()

    fun getBank(accountNumber: String):Bank = dataSource.getBank(accountNumber)

    fun addBank(newBank: Bank) : Bank = dataSource.createBank(newBank)

    fun patchBank(bankToUpdate: Bank) = dataSource.patchBank(bankToUpdate)

    fun deleteBank(accountNumber: String) = dataSource.deleteBank(accountNumber)
}