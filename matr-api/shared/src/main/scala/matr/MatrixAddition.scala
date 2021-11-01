package matr

/** Type class for the addition of Matrices.
  */
trait MatrixAddition[R <: Int, C <: Int, T]:

   def plus(lhs: M, rhs: M): M

   type M = Matrix[R, C, T]
