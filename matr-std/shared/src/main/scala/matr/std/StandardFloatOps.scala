package matr.std:

   import matr.ElementMultiplication

   trait StandardFloatOps:

      given floatMultiplication: ElementMultiplication[Float, Float] with
         def times(lhs: Float, rhs: Float): Out = lhs * rhs
         type Out = Float

      given floatBigDecimalMultiplication: ElementMultiplication[Float, BigDecimal] with
         def times(lhs: Float, rhs: BigDecimal): Out = lhs.toDouble * rhs
         type Out = BigDecimal

      given floatDoubleMultiplication: ElementMultiplication[Float, Double] with
         def times(lhs: Float, rhs: Double): Out = lhs * rhs
         type Out = Double

      given floatIntMultiplication: ElementMultiplication[Float, Int] with
         def times(lhs: Float, rhs: Int): Out = lhs * rhs
         type Out = Float

      given floatLongMultiplication: ElementMultiplication[Float, Long] with
         def times(lhs: Float, rhs: Long): Out = lhs * rhs
         type Out = Float

   object StandardFloatOps extends StandardFloatOps
