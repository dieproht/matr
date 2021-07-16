package matr.dflt:

  import matr.Determinant
  import matr.Matrix
  import math.Numeric.Implicits.infixNumericOps
  

  /** Default determinant implementation based on Laplace expansion. 
   * Please pay attention: its algorithmic simplicity comes with a high perfomance cost!
   */ 
  trait DefaultDeterminant: 

    given defaultDeterminant[R <: Int, C <: Int, T](using Determinant.Validated[R, C])
                                                   (using num: Numeric[T])
                                                   : Determinant[R, C, T] with 

      def det(m: Matrix[R, C, T]): T = 
        calcDet(m.apply, m.rowDim)

      private def calcDet(get: (Int, Int) => T, dim: Int): T = 
        if dim == 1 then get(0, 0)
        else if dim == 2 then calcDet2x2(get)
        else 
          var current: T = num.zero
          for colIdx <- 0 until dim do 
            val _get = excludeRowAndCol(0, colIdx, get)
            val coeff = if colIdx % 2 == 0 then num.one else num.negate(num.one)
            current = current + coeff * get(0, colIdx) * calcDet(_get, dim -1)
          current

      private def calcDet2x2(get: (Int, Int) => T): T = 
        get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0)

      private def excludeRowAndCol(excludeRow: Int, excludeCol: Int, ref: (Int, Int) => T): (Int, Int) => T =
        (rowIdx: Int, colIdx: Int) => 
          val rowIdxN = if rowIdx >= excludeRow then rowIdx + 1 else rowIdx
          val colIdxN = if colIdx >= excludeCol then colIdx + 1 else colIdx
          ref(rowIdxN, colIdxN)


  object DefaultDeterminant extends DefaultDeterminant
