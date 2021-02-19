package matr.dflt:

  import matr.ElementAddition
  import matr.MatrixAddition
  import matr.MatrixFactory

  
  trait DefaultMatrixAddition:

    given defaultMatrixAddition[R <: Int, C <: Int, T]
                               (using a: ElementAddition[T]) 
                               (using mf: MatrixFactory[R, C, T])
                               : MatrixAddition[R, C, T] with

      def plus(lhs: M, rhs: M): M = 
        lhs.combine(rhs)(a.plus)


  object DefaultMatrixAddition extends DefaultMatrixAddition
