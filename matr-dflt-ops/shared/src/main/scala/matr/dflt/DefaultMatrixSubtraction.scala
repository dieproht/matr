package matr.dflt:

  import matr.MatrixFactory
  import matr.MatrixSubtraction
  import matr.ElementSubtraction

  trait DefaultMatrixSubtraction:

    given defaultMatrixSubtraction[R <: Int, C <: Int, T]
                                  (using a: ElementSubtraction[T]) 
                                  (using mf: MatrixFactory[R, C, T])
                                  : MatrixSubtraction[R, C, T] with

      def minus(lhs: M, rhs: M): M = 
        lhs.combine(rhs)(a.minus)


  object DefaultMatrixSubtraction extends DefaultMatrixSubtraction
