package matr.impl:

  import matr.Matrix

  
  private[matr] object Iterate:

    inline def apply[R <: Int, C <: Int, T](m: Matrix[R, C, T], op: (Int, Int) => Unit): Unit =
      var rowIdx: Int = 0
      while rowIdx < m.rowDim do 
        var colIdx: Int = 0
        while colIdx < m.colDim do 
          op(rowIdx, colIdx)
          colIdx = colIdx + 1
        rowIdx = rowIdx + 1
