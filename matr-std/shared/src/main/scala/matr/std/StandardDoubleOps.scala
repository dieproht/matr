package matr.std:
  
  import matr.ElementMultiplication

  trait StandardDoubleOps:
  
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


  object StandardDoubleOps extends StandardDoubleOps
