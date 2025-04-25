package matr

/** Type class for the division of Matrix elements. It is provided because, unlike [[scala.math.Numeric]], it allows the
  * result type to be calculated at compile-time.
  */
trait ElementDivision[A, B]:
    def div(lhs: A, rhs: B): Out
    type Out

object ElementDivision:
    type Aux[A0, B0, Out0] =
        ElementDivision[A0, B0]:
            type Out = Out0
