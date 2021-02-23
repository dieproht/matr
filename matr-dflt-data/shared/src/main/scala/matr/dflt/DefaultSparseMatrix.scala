package matr.dflt:

  import matr.Matrix

  
  case class DefaultSparseMatrix[R <: Int, C <: Int, T](private val elements: Map[(Int, Int), T])
                                                       (using num: Numeric[T])
                                                       (using Matrix.DimsOK[R, C] =:= true)
                                                       (using vr: ValueOf[R], vc: ValueOf[C]) 
                                                       extends Matrix[R, C, T]:
    lhs =>

    override def apply(rowIdx: Int, colIdx: Int): T = 
      Matrix.IndexOK(rowIdx, colIdx, rowDim, colDim)
      elements.getOrElse((rowIdx, colIdx), num.zero)

    override def toString(): String = s"DefaultSparseMatrix(${elements.mkString(",")})"
