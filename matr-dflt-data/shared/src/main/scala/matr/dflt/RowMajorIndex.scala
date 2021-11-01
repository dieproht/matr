package matr.dflt:

   object RowMajorIndex:

      def toIdx(rowIdx: Int, colIdx: Int, numCols: Int): Int = rowIdx * numCols + colIdx

      def fromIdx(idx: Int, numCols: Int): (Int, Int) = (idx / numCols, idx % numCols)
