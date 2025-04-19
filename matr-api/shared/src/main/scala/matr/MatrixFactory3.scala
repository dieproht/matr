package matr

import matr.TupleSupport.MatrixTupleReader
import matr.TupleSupport.RowTupleReader
import matr.util.RowMajorIndex

import scala.collection.concurrent.TrieMap
import scala.compiletime.ops.any.==
import scala.reflect.ClassTag

/** Central entry point for creating Matrices.
  */
class MatrixFactory3[R <: Int, C <: Int, T] private (using
 ValueOf[R],
 ValueOf[C],
 Numeric[T],
 ClassTag[T],
 Matrix.Requirements.NonNegativeDimensions[R, C],
 Matrix.CreateMatrixBuilder[R, C, T]
):
   private val rowDim: R = valueOf[R]
   private val colDim: C = valueOf[C]
   private val num: Numeric[T] = summon[Numeric[T]]

   private val createBuilder: Matrix.CreateMatrixBuilder[R, C, T] =
      summon[Matrix.CreateMatrixBuilder[R, C, T]]

   /** Creates a Matrix containing the specified elements, assuming row-major order. Dimensions will
     * be checked at runtime.
     */
   def rowMajor(elements: T*): Matrix[R, C, T] =
      require(
         elements.length == rowDim * colDim,
         s"Size of given element collection must be ${rowDim * colDim}, but was ${elements.size}"
      )
      val buildr: Matrix.Builder[R, C, T] = createBuilder
      var idx: Int = 0
      while (idx < elements.length) do
         val (rowIdx, colIdx) = RowMajorIndex.fromIdx(idx, colDim)
         buildr(rowIdx, colIdx) = elements(idx)
         idx = idx + 1
      buildr.result

   /** Creates a Matrix containing the elements returned by the specified function.
     *
     * @param fillElem
     *   function returning the element at the specified position (row index, column index)
     */
   def tabulate(fillElem: (Int, Int) => T): Matrix[R, C, T] =
      val buildr: Matrix.Builder[R, C, T] = createBuilder
      buildr.iterate { (rowIdx, colIdx) =>
         buildr(rowIdx, colIdx) = fillElem(rowIdx, colIdx)
      }
      buildr.result

   /** Creates a Matrix of zeros.
     */
   def zeros: Matrix[R, C, T] = tabulate((_, _) => num.zero)

   /** Creates a Matrix of ones.
     */
   def ones: Matrix[R, C, T] = tabulate((_, _) => num.one)

   /** Creates the identity Matrix.
     */
   def identity(using Matrix.Requirements.IsSquare[R, C]): Matrix[R, C, T] = tabulate {
      (rowIdx, colIdx) =>
         if rowIdx == colIdx then
            num.one
         else
            num.zero
   }

   /** Creates a Matrix with the specified elements that are structured row by row from tuples.
     *
     * Dimensions will be checked at compile-time.
     *
     * When invoking this method the following import has to be in place:
     * ```
     * import matr.TupleSupport.given
     * ```
     *
     * The creation of i.e. a 2x3 Matrix looks like this:
     * ```
     * val m = MatrixFactory[2, 3, Int].fromTuple(
     *   (11, 12, 13),
     *   (21, 22, 23)
     * )
     * ```
     */
   def fromTuple[MatrixTuple <: Tuple, RowTuple <: Tuple]
            (matrixTuple: MatrixTuple)
            (using
             matrixReader: MatrixTupleReader[MatrixTuple, RowTuple],
             rowReader: RowTupleReader[RowTuple, T]
            )
            (using Tuple.Size[MatrixTuple] =:= R, Tuple.Size[RowTuple] =:= C)
            : Matrix[R, C, T] =
      val buildr: Matrix.Builder[R, C, T] = createBuilder
      val setElem: (Int, Int, T) => Unit =
         (rowIdx, colIdx, elem) => buildr.update(rowIdx, colIdx, elem)
      val setRow: (Int, RowTuple) => Unit =
         (rowIdx, rowTuple) => rowReader.readRow(rowIdx, rowTuple, colDim, setElem)
      matrixReader.readMatrix(matrixTuple, rowDim, setRow)
      buildr.result

object MatrixFactory3:

   private val cachedFactories: TrieMap[String, MatrixFactory3[? <: Int, ? <: Int, ?]] = TrieMap()

   private def cacheKey[R <: Int, C <: Int, T](using ValueOf[R], ValueOf[C], ClassTag[T]): String =
      s"${valueOf[R]}-${valueOf[C]}-${summon[ClassTag[T]].runtimeClass.getName}"

      /** Returns a MatrixFactory matching the given dimensions and data type.
        */
   def apply[R <: Int, C <: Int, T]
            (using
             Numeric[T],
             ClassTag[T],
             ValueOf[R],
             ValueOf[C],
             Matrix.Requirements.NonNegativeDimensions[R, C],
             Matrix.CreateMatrixBuilder[R, C, T]
            )
            : MatrixFactory3[R, C, T] = //
      cachedFactories
         .getOrElseUpdate(cacheKey[R, C, T], new MatrixFactory3[R, C, T])
         .asInstanceOf[MatrixFactory3[R, C, T]]
