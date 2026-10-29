package matr.std

import matr.ElementMultiplication

trait StandardIntOps:

    given intMultiplication: ElementMultiplication[Int, Int]:
        def times(lhs: Int, rhs: Int): Out = lhs * rhs
        type Out = Int

    given intBigDecimalMultiplication: ElementMultiplication[Int, BigDecimal]:
        def times(lhs: Int, rhs: BigDecimal): Out = lhs * rhs
        type Out = BigDecimal

    given intDoubleMultiplication: ElementMultiplication[Int, Double]:
        def times(lhs: Int, rhs: Double): Out = lhs * rhs
        type Out = Double

    given intFloatMultiplication: ElementMultiplication[Int, Float]:
        def times(lhs: Int, rhs: Float): Out = lhs * rhs
        type Out = Float

    given intLongMultiplication: ElementMultiplication[Int, Long]:
        def times(lhs: Int, rhs: Long): Out = lhs * rhs
        type Out = Long

object StandardIntOps extends StandardIntOps
