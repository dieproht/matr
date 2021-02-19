package matr:

  trait ElementMultiplication[A, B]: 

    def times(lhs: A, rhs: B): Out

    type Out


  object ElementMultiplication:

    type Aux[A0, B0, Out0] = ElementMultiplication[A0, B0] { type Out = Out0 }
