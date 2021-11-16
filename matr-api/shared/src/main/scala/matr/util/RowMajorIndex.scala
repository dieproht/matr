package matr.util

/** Utility for converting zero-based element position to one-dimensional row-major index and back.
  */
object RowMajorIndex:

   def toIdx(rowIdx: Int, colIdx: Int, numCols: Int): Int = rowIdx * numCols + colIdx

   def fromIdx(idx: Int, numCols: Int): (Int, Int) = (idx / numCols, idx % numCols)
