package matr.std:
  
  import matr.ElementAddition
  import matr.ElementMultiplication
  import matr.SpecialValues
  import matr.ElementSubtraction

  trait StandardDoubleOps:
  
    given doubleAddition: ElementAddition[Double] with
      def plus(lhs: Double, rhs: Double): Double = lhs + rhs
      
    given doubleSubtraction: ElementSubtraction[Double] with
      def minus(lhs: Double, rhs: Double): Double = lhs - rhs

    given doubleMultiplication: ElementMultiplication[Double, Double] with
      def times(lhs: Double, rhs: Double): Out = lhs * rhs
      type Out = Double

    given doubleFloatMultiplication: ElementMultiplication[Double, Float] with
      def times(lhs: Double, rhs: Float): Out = lhs * rhs
      type Out = Double
      
    given doubleIntMultiplication: ElementMultiplication[Double, Int] with
      def times(lhs: Double, rhs: Int): Out = lhs * rhs
      type Out = Double

    given doubleLongMultiplication: ElementMultiplication[Double, Long] with
      def times(lhs: Double, rhs: Long): Out = lhs * rhs
      type Out = Double

    given doubleSpecialValues: SpecialValues[Double] with
      def isZero(value: Double): Boolean = value == zero
      val zero: Double = 0.0
      val one: Double = 1.0


  object StandardDoubleOps extends StandardDoubleOps
