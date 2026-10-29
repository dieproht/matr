package matr.std

import matr.ElementDivision
import matr.ElementMultiplication

trait StandardBigDecimalOps:

    given bigDecimalMultiplication: ElementMultiplication[BigDecimal, BigDecimal]:
        def times(lhs: BigDecimal, rhs: BigDecimal): Out = lhs * rhs
        type Out = BigDecimal

    given bigDecimalDoubleMultiplication: ElementMultiplication[BigDecimal, Double]:
        def times(lhs: BigDecimal, rhs: Double): Out = lhs * rhs
        type Out = BigDecimal

    given bigDecimalFloatMultiplication: ElementMultiplication[BigDecimal, Float]:
        def times(lhs: BigDecimal, rhs: Float): Out = lhs * rhs.toDouble
        type Out = BigDecimal

    given bigDecimalIntMultiplication: ElementMultiplication[BigDecimal, Int]:
        def times(lhs: BigDecimal, rhs: Int): Out = lhs * rhs
        type Out = BigDecimal

    given bigDecimalLongMultiplication: ElementMultiplication[BigDecimal, Long]:
        def times(lhs: BigDecimal, rhs: Long): Out = lhs * rhs
        type Out = BigDecimal

    given bigDecimalDivision: ElementDivision[BigDecimal, BigDecimal]:
        def div(lhs: BigDecimal, rhs: BigDecimal): Out = lhs / rhs
        type Out = BigDecimal

object StandardBigDecimalOps extends StandardBigDecimalOps
