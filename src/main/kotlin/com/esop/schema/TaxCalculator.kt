package com.esop.schema

import com.esop.InvalidEsopPriceException
import com.esop.InvalidEsopQuantityException
import com.esop.InvalidEsopTypeException
import java.lang.Long.min
import kotlin.math.round

class TaxCalculator : Chargeable {

    override fun calculateCharge(quantity: Long, price: Long, esopType: String): Long {
        if (quantity <= 0) throw InvalidEsopQuantityException()

        if (price <= 0) throw InvalidEsopPriceException()

        when (esopType) {
            "PERFORMANCE" -> {

                if (quantity in PerformanceTaxSlab.FIRST_SLAB.minLimit..PerformanceTaxSlab.FIRST_SLAB.maxLimit)
                    return min(
                        PerformanceTaxSlab.FIRST_SLAB.cap,
                        round(quantity * price * PerformanceTaxSlab.FIRST_SLAB.pct).toLong()
                    )

                if (quantity in PerformanceTaxSlab.SECOND_SLAB.minLimit..PerformanceTaxSlab.SECOND_SLAB.maxLimit)
                    return min(
                        PerformanceTaxSlab.SECOND_SLAB.cap,
                        round(quantity * price * PerformanceTaxSlab.SECOND_SLAB.pct).toLong()
                    )

                return round(quantity * price * PerformanceTaxSlab.THIRD_SLAB.pct).toLong()
            }

            "NON_PERFORMANCE" -> {

                if (quantity in NonPerformanceTaxSlab.FIRST_SLAB.minLimit..NonPerformanceTaxSlab.FIRST_SLAB.maxLimit)
                    return min(
                        NonPerformanceTaxSlab.FIRST_SLAB.cap,
                        round(quantity * price * NonPerformanceTaxSlab.FIRST_SLAB.pct).toLong()
                    )

                if (quantity in NonPerformanceTaxSlab.SECOND_SLAB.minLimit..NonPerformanceTaxSlab.SECOND_SLAB.maxLimit)
                    return min(
                        NonPerformanceTaxSlab.SECOND_SLAB.cap,
                        round(quantity * price * NonPerformanceTaxSlab.SECOND_SLAB.pct).toLong()
                    )

                return round(quantity * price * NonPerformanceTaxSlab.THIRD_SLAB.pct).toLong()
            }

            else -> throw InvalidEsopTypeException()
        }

    }


}

enum class PerformanceTaxSlab(val minLimit: Long, val maxLimit: Long, val pct: Double, val cap: Long) {
    FIRST_SLAB(1, 100, 0.02, 50),
    SECOND_SLAB(101, 50000, 0.0225, 50),
    THIRD_SLAB(50000, Long.MAX_VALUE, 0.025, Long.MAX_VALUE)
}

enum class NonPerformanceTaxSlab(val minLimit: Long, val maxLimit: Long, val pct: Double, val cap: Long) {
    FIRST_SLAB(1, 100, 0.01, 20),
    SECOND_SLAB(101, 50000, 0.0125, 20),
    THIRD_SLAB(50000, Long.MAX_VALUE, 0.015, Long.MAX_VALUE)
}