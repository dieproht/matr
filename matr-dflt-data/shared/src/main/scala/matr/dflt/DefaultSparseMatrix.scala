package matr.dflt:

  import matr.Matrix
  import matr.SpecialValues

  
  case class DefaultSparseMatrix[R <: Int, C <: Int, T](private val elements: Map[(Int, Int), T])
                                                       (using Matrix.DimsOK[R, C] =:= true)
                                                       (using vr: ValueOf[R], vc: ValueOf[C]) 
                                                       (using sp: SpecialValues[T])
                                                       extends Matrix[R, C, T]:
    lhs =>

    override def apply(rowIdx: Int, colIdx: Int): T = 
      Matrix.IndexOK(rowIdx, colIdx, rowDim, colDim)
      elements.getOrElse((rowIdx, colIdx), sp.zero)

    override def toString(): String = s"DefaultSparseMatrix(${elements.mkString(",")})"
