package matr.std:

  import matr.ElementMultiplication


  trait StandardLongOps:

    given longMultiplication: ElementMultiplication[Long, Long] with
      def times(lhs: Long, rhs: Long): Out = lhs * rhs
      type Out = Long

    given longBigDecimalMultiplication: ElementMultiplication[Long, BigDecimal] with
      def times(lhs: Long, rhs: BigDecimal): Out = lhs * rhs
      type Out = BigDecimal

    given longDoubleMultiplication: ElementMultiplication[Long, Double] with
      def times(lhs: Long, rhs: Double): Out = lhs * rhs
      type Out = Double

    given longFloatMultiplication: ElementMultiplication[Long, Float] with
      def times(lhs: Long, rhs: Float): Out = lhs * rhs
      type Out = Float

    given longIntMultiplication: ElementMultiplication[Long, Int] with
      def times(lhs: Long, rhs: Int): Out = lhs * rhs
      type Out = Long


  object StandardLongOps extends StandardLongOps
