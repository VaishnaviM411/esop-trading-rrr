package com.esop.repository

import com.esop.TransactionTaxLessThanZeroException
import jakarta.inject.Singleton
import java.math.BigInteger

@Singleton
class TaxRecords {

    private var totalTransactionTax: BigInteger = BigInteger("0")

    fun addTransactionTax(tax: Long) {
        if (tax < 0)
            throw TransactionTaxLessThanZeroException()
        totalTransactionTax += tax.toBigInteger()
    }

    fun getTransactionTax(): BigInteger {
        return totalTransactionTax
    }
}