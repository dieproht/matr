package matr: 

  trait MatrixMultiplication[R <: Int, C <: Int, L <: Int, T, U]:

    def dot(lhs: Lhs, rhs: Rhs): Out

    type Lhs = Matrix[R, C, T]

    type Rhs = Matrix[C, L, U]

    type Out
