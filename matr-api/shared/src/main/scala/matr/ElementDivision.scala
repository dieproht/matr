package matr

trait ElementDivision[A, B]:

   def dividedBy(lhs: A, rhs: B): Out

   type Out

object ElementDivision:

   type Aux[A0, B0, Out0] =
      ElementDivision[A0, B0] {
         type Out = Out0
      }
