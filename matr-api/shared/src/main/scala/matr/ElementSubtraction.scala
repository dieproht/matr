package matr:

  trait ElementSubtraction[A]:
    
    def minus(lhs: A, rhs: A): A
