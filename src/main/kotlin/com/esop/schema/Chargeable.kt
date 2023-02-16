package com.esop.schema

interface Chargeable {
    fun calculateCharge(quantity: Long, price: Long, esopType: String): Long

}