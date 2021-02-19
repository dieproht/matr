package matr:

  trait ElementAddition[A]:
    
    def plus(lhs: A, rhs: A): A
