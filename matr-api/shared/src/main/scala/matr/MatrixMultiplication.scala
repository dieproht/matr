package matr: 

  /** Type class for the multiplication (calculating the dot product) of Matrices. 
    * The result's element type will be calculated at compile-time, based on the element type of 
    * the operands. 
    * 
    * @tparam R Row dimension of the left-hand side operand
    * @tparam C Column dimension of the left-hand side operand and 
    *   row dimension of the right-hand side operand
    * @tparam L Column dimension of the right-hand side operand
    * @tparam T Element type of the left-hand side operand
    * @tparam U Element type of the right-hand side operand
    */ 
  trait MatrixMultiplication[R <: Int, C <: Int, L <: Int, T, U]:

    def dot(lhs: Lhs, rhs: Rhs): Out

    type Lhs = Matrix[R, C, T]

    type Rhs = Matrix[C, L, U]

    type Out
