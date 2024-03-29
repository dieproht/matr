package matr

import matr.TupleSupport.MatrixTupleReader
import matr.TupleSupport.RowTupleReader
import matr.util.RowMajorIndex

import scala.compiletime.ops.any.==

/** Central entry point for creating Matrices. Modules implementing the `Matrix` trait should also
  * provide an instance of this type class.
  */
trait MatrixFactory[R <: Int, C <: Int, T](using Numeric[T])(using ValueOf[R], ValueOf[C]):

   protected val rowDim: R = valueOf[R]
   protected val colDim: C = valueOf[C]
   protected val num: Numeric[T] = summon[Numeric[T]]

   /** Returns a new `Builder` instance.
     */
   def builder: MatrixFactory.Builder[R, C, T]

   /** Creates a Matrix containing the specified elements, assuming row-major order. Dimensions will
     * be checked at runtime.
     */
   def rowMajor(elements: T*): Matrix[R, C, T] =
      require(
         elements.length == rowDim * colDim,
         s"Size of given element collection must be ${rowDim * colDim}, but was ${elements.size}"
      )
      val buildr: MatrixFactory.Builder[R, C, T] = builder
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
      val buildr: MatrixFactory.Builder[R, C, T] = builder
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

   /** Creates a Matrix with the specified elements that are structured row by row by tuples.
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
      val buildr: MatrixFactory.Builder[R, C, T] = builder
      val setElem: (Int, Int, T) => Unit =
         (rowIdx, colIdx, elem) => buildr.update(rowIdx, colIdx, elem)
      val setRow: (Int, RowTuple) => Unit =
         (rowIdx, rowTuple) => rowReader.readRow(rowIdx, rowTuple, colDim, setElem)
      matrixReader.readMatrix(matrixTuple, rowDim, setRow)
      buildr.result

object MatrixFactory:

   /** A `Builder` is a "Matrix under construction" that itself forms a (mutable) Matrix. Utilizing
     * `Builder` is the preferred way of creating new Matrices in Matrix operations.
     *
     * _One_ instance of a `Builder` is used to create _one_ Matrix. This means that invoking the
     * same `Builder` instance multiple times is OK, but letting a `Builder` leave a local scope is
     * not OK (it's mutable!).
     *
     * Implementations of this trait must return zero for uninitialized element positions.
     */
   trait Builder[R <: Int, C <: Int, T] extends Matrix[R, C, T]:

      /** Sets the specified element at the specified position.
        */
      def update(rowIdx: Int, colIdx: Int, v: T): this.type

      /** Creates the Matrix resulting from this `Builder`.
        */
      def result: Matrix[R, C, T]

   /** Returns the implicitly available `MatrixFactory`.
     */
   def apply[R <: Int, C <: Int, T](using mf: MatrixFactory[R, C, T]): MatrixFactory[R, C, T] = mf
