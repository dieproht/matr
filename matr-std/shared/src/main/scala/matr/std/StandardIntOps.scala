package matr.std: 

  import matr.ElementAddition
  import matr.ElementMultiplication
  import matr.SpecialValues
  import matr.ElementSubtraction


  trait StandardIntOps:

    given intAddition: ElementAddition[Int] with
      def plus(lhs: Int, rhs: Int): Int = lhs + rhs

    given intSubtraction: ElementSubtraction[Int] with
      def minus(lhs: Int, rhs: Int): Int = lhs - rhs

    given intMultiplication: ElementMultiplication[Int, Int] with
      def times(lhs: Int, rhs: Int): Out = lhs * rhs
      type Out = Int

    given intDoubleMultiplication: ElementMultiplication[Int, Double] with
      def times(lhs: Int, rhs: Double): Out = lhs * rhs      
      type Out = Double

    given intFloatMultiplication: ElementMultiplication[Int, Float] with
      def times(lhs: Int, rhs: Float): Out = lhs * rhs      
      type Out = Float

    given intLongMultiplication: ElementMultiplication[Int, Long] with
      def times(lhs: Int, rhs: Long): Out = lhs * rhs      
      type Out = Long

    given intSpecialValues: SpecialValues[Int] with
      def isZero(value: Int): Boolean = value == zero
      val zero: Int = 0
      val one: Int = 1


  object StandardIntOps extends StandardIntOps
