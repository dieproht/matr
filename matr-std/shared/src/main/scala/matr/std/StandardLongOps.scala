package matr.std:

  import matr.ElementAddition
  import matr.ElementMultiplication
  import matr.SpecialValues
  import matr.ElementSubtraction


  trait StandardLongOps:

    given longAddition: ElementAddition[Long] with
      def plus(lhs: Long, rhs: Long): Long = lhs + rhs

    given longSubtraction: ElementSubtraction[Long] with
      def minus(lhs: Long, rhs: Long): Long = lhs - rhs
      
    given longMultiplication: ElementMultiplication[Long, Long] with
      def times(lhs: Long, rhs: Long): Out = lhs * rhs
      type Out = Long

    given longDoubleMultiplication: ElementMultiplication[Long, Double] with
      def times(lhs: Long, rhs: Double): Out = lhs * rhs
      type Out = Double

    given longFloatMultiplication: ElementMultiplication[Long, Float] with
      def times(lhs: Long, rhs: Float): Out = lhs * rhs
      type Out = Float

    given longIntMultiplication: ElementMultiplication[Long, Int] with
      def times(lhs: Long, rhs: Int): Out = lhs * rhs
      type Out = Long

    given longSpecialValues: SpecialValues[Long] with
      def isZero(value: Long): Boolean = value == zero
      val zero: Long = 0l
      val one: Long = 1l


  object StandardLongOps extends StandardLongOps
