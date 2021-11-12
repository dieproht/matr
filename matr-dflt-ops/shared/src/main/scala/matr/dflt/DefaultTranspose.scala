package matr.dflt

  import matr.Matrix 
  import matr.MatrixFactory
  import matr.Transpose

  trait DefaultTranspose:

    given defaultTranspose[R <: Int, C <: Int, T]
                          (using Matrix.DimensionRequirements[C, R])
                          (using ValueOf[R], ValueOf[C])
                          : Transpose[R, C, T] with 

      def transpose(m: Matrix[R, C, T]): Matrix[C, R, T] = 
        DefaultTranspose.TransposeView(m)


  object DefaultTranspose extends DefaultTranspose:

    final class TransposeView[OrigR <: Int, OrigC <: Int, T](orig: Matrix[OrigR, OrigC, T])
                             (using Matrix.DimensionRequirements[OrigC, OrigR])
                             (using ValueOf[OrigR], ValueOf[OrigC])
                             extends Matrix[OrigC, OrigR, T]:
      
      override def apply(rowIdx: Int, colIdx: Int): T = 
        Matrix.IndexOK(rowIdx, colIdx, rowDim, colDim)
        orig(colIdx, rowIdx)


      override def toString(): String = s"TransposeView(${orig.toString})"
