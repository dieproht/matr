package matr

/** Type class for the multiplication of Matrix elements. It is provided because, unlike
  * [[scala.math.Numeric]], it allows the result type to be calculated at compile-time.
  */
trait ElementMultiplication[A, B]:

   def times(lhs: A, rhs: B): Out

   type Out

object ElementMultiplication:

   type Aux[A0, B0, Out0] =
      ElementMultiplication[A0, B0]:
         type Out = Out0
