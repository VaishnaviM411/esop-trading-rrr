package com.esop.stub

import com.esop.schema.Chargeable

const val DUMMY_TAX = 10L

class DummyTaxCalculator : Chargeable {
    override fun calculateCharge(quantity: Long, price: Long, esopType: String): Long {
        return DUMMY_TAX
    }

}
