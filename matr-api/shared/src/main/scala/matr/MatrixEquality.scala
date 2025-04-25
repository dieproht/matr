package matr

/** Type class for testing two Matrices on equality.
  */
trait MatrixEquality[R <: Int, C <: Int, T]:
    def matricesEqual(lhs: M, rhs: M): Boolean
    type M = Matrix[R, C, T]
