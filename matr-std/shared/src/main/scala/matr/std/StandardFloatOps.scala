package matr.std:

  import matr.ElementAddition
  import matr.ElementMultiplication
  import matr.SpecialValues
  import matr.ElementSubtraction

  trait StandardFloatOps:

    given floatAddition: ElementAddition[Float] with
      def plus(lhs: Float, rhs: Float): Float = lhs + rhs

    given floatSubtraction: ElementSubtraction[Float] with
      def minus(lhs: Float, rhs: Float): Float = lhs - rhs

    given floatMultiplication: ElementMultiplication[Float, Float] with
      def times(lhs: Float, rhs: Float): Out = lhs * rhs
      type Out = Float

    given floatDoubleMultiplication: ElementMultiplication[Float, Double] with
      def times(lhs: Float, rhs: Double): Out = lhs * rhs
      type Out = Double

    given floatIntMultiplication: ElementMultiplication[Float, Int] with
      def times(lhs: Float, rhs: Int): Out = lhs * rhs
      type Out = Float

    given floatLongMultiplication: ElementMultiplication[Float, Long] with
      def times(lhs: Float, rhs: Long): Out = lhs * rhs
      type Out = Float

    given floatSpecialValues: SpecialValues[Float] with
      def isZero(value: Float): Boolean = value == zero
      val zero: Float = 0.0f
      val one: Float = 1.0f


  object StandardFloatOps extends StandardFloatOps
