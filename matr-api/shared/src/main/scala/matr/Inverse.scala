package matr

/** Type class for calculating the inverse of a Matrix.
  */
trait Inverse[R <: Int, C <: Int, T](using Matrix.Requirements.IsSquare[R, C]):

   def inv(m: M): M

   type M = Matrix[R, C, T]

case class MatrixNotInvertibleException() extends ArithmeticException("Matrix not invertible")
