package matr

trait Inverse[R <: Int, C <: Int, T](using Matrix.Requirements.IsSquare[R, C]):

   def inv(m: M): M

   type M = Matrix[R, C, T]
