package matr

/** Type class for the subtraction of Matrices.
  */
trait MatrixSubtraction[R <: Int, C <: Int, T]:
    def minus(lhs: M, rhs: M): M
    type M = Matrix[R, C, T]
