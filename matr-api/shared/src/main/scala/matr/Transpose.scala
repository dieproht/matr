package matr

/** Type class for transposing a Matrix.
  */
trait Transpose[R <: Int, C <: Int, T]:

   def transpose(m: Matrix[R, C, T]): Matrix[C, R, T]
