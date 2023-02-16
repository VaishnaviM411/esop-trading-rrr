package com.esop.schema

import com.esop.InvalidEsopPriceException
import com.esop.InvalidEsopQuantityException
import com.esop.InvalidEsopTypeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionTaxTest {

    val taxCalculator = TaxCalculator()

    @Test
    fun `It should calculate tax for PERFORMANCE esop quantity less than 100 without cap`() {
        val quantity = 50L
        val price = 10L
        val esopType = "PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(10, expectedTax)
    }

    @Test
    fun `It should calculate tax for PERFORMANCE esop quantity less than 100 with cap`() {
        val quantity = 50L
        val price = 100L
        val esopType = "PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(50, expectedTax)
    }

    @Test
    fun `It should calculate tax for PERFORMANCE esop quantity between 100 and 50k without cap`() {
        val quantity = 200L
        val price = 1L
        val esopType = "PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(4, expectedTax)
    }

    @Test
    fun `It should calculate tax for PERFORMANCE esop quantity between 100 and 50k with cap`() {
        val quantity = 200L
        val price = 100L
        val esopType = "PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(50, expectedTax)
    }

    @Test
    fun `It should throw exception for non-positive quantity`() {
        val quantity = -10L
        val price = 10L
        val esopType = "PERFORMANCE"

        assertThrows(
            InvalidEsopQuantityException::class.java
        ) {
            taxCalculator.calculateCharge(quantity, price, esopType)
        }
    }

    @Test
    fun `It should throw exception for non-positive price`() {
        val quantity = 10L
        val price = -10L
        val esopType = "PERFORMANCE"

        assertThrows(
            InvalidEsopPriceException::class.java
        ) {
            taxCalculator.calculateCharge(quantity, price, esopType)
        }
    }

    @Test
    fun `It should throw exception for invalid esopType`() {
        val quantity = 10L
        val price = 10L
        val esopType = "PERF"

        assertThrows(
            InvalidEsopTypeException::class.java
        ) {
            taxCalculator.calculateCharge(quantity, price, esopType)
        }
    }


    @Test
    fun `It should calculate tax for PERFORMANCE esop quantity above 50k`() {
        val quantity = 60000L
        val price = 100L
        val esopType = "PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(150000, expectedTax)
    }

    @Test
    fun `It should calculate tax for NON_PERFORMANCE esop quantity less than 100 without cap`() {
        val quantity = 50L
        val price = 10L
        val esopType = "NON_PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(5, expectedTax)
    }

    @Test
    fun `It should calculate tax for NON_PERFORMANCE esop quantity less than 100 with cap`() {
        val quantity = 50L
        val price = 100L
        val esopType = "NON_PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(20, expectedTax)
    }

    @Test
    fun `It should calculate tax for NON_PERFORMANCE esop quantity between 100 and 50k without cap`() {
        val quantity = 200L
        val price = 1L
        val esopType = "NON_PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(2, expectedTax)
    }

    @Test
    fun `It should calculate tax for NON_PERFORMANCE esop quantity between 100 and 50k with cap`() {
        val quantity = 200L
        val price = 100L
        val esopType = "NON_PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(20, expectedTax)
    }

    @Test
    fun `It should calculate tax for NON_PERFORMANCE esop quantity above 50k`() {
        val quantity = 60000L
        val price = 100L
        val esopType = "NON_PERFORMANCE"

        val expectedTax = taxCalculator.calculateCharge(quantity, price, esopType)

        assertEquals(90000, expectedTax)
    }

}