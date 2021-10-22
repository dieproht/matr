package matr

import scala.compiletime.ops.any.==
import matr.TupleSupport.MatrixTupleReader
import matr.TupleSupport.RowTupleReader

/** Central entry point for creating Matrices. Modules implementing the `Matrix` trait should also
  * provide an instance of this type class.
  */
trait MatrixFactory[R <: Int, C <: Int, T]:

   /** Returns a new `Builder` instance.
     */
   def builder: MatrixFactory.Builder[R, C, T]

   /** Creates a Matrix containing the specified elements, assuming row-major order. Dimensions will
     * be checked at runtime.
     */
   def rowMajor(elements: T*): Matrix[R, C, T]

   /** Creates a Matrix containing the elements returned by the specified function.
     *
     * @param fillElem
     *   function returning the element at the specified position (row index, column index)
     */
   def tabulate(fillElem: (Int, Int) => T): Matrix[R, C, T]

   /** Creates a Matrix of zeros.
     */
   def zeros: Matrix[R, C, T]

   /** Creates a Matrix of ones.
     */
   def ones: Matrix[R, C, T]

   /** Creates the identity Matrix.
     */
   def identity(using Matrix.IsSquare[R, C] =:= true): Matrix[R, C, T]

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
         (using valRowDim: ValueOf[R], valColDim: ValueOf[C])
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
         (rowIdx, rowTuple) => rowReader.readRow(rowIdx, rowTuple, valColDim.value, setElem)
      matrixReader.readMatrix(matrixTuple, valRowDim.value, setRow)
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
   def apply[R <: Int, C <: Int, T](using mf: MatrixFactory[R, C, T]): MatrixFactory[R, C, T] =
      mf
