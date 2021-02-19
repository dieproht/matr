package matr

  trait Transpose[R <: Int, C <: Int, T]:
    
    def transpose(m: Matrix[R, C, T]): Matrix[C, R, T]
