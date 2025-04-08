package matr

import scala.compiletime.ops.any.==
import scala.compiletime.ops.boolean.&&
import scala.compiletime.ops.int.+
import scala.compiletime.ops.int.-
import scala.compiletime.ops.int.<
import scala.compiletime.ops.int.>
import scala.compiletime.ops.int.>=

/** Represents a matrix in the mathematical sense and is the central type of this library. Row
  * dimension, column dimension and the data type of the elements are set at compile-time. Elements
  * are indexed zero-based. Concrete subclasses of this trait implement immutable storage and access
  * to the elements of the matrix. Implementations for non-mathematical helper methods (`mkString`
  * etc.) are provided here, but can be overridden. Mathematical operations are handled by type
  * classes.
  *
  * Matrix elements are indexed according to the following pattern:
  * | 0,0        | 0,1      | 0,2 | 0,colIdx      | ... | 0,colDim-1        |
  * |:-----------|:---------|:----|:--------------|:----|:------------------|
  * | 1,0        | 1,1      | 1,2 | 1,colIdx      | ... | ...               |
  * | 2,0        | 2,1      | 2,2 | ...           | ... | ...               |
  * | rowIdx,0   | rowIdx,1 | ... | rowIdx,colIdx | ... | ...               |
  * | ...        | ...      | ... | ...           | ... | ...               |
  * | rowDim-1,0 | ...      | ... | ...           | ... | rowDim-1,colDim-1 |
  *
  * @tparam R
  *   row dimension
  * @tparam C
  *   column dimension
  * @tparam T
  *   element type
  */
trait Matrix[R <: Int, C <: Int, T]
         (using Matrix.Requirements.NonNegativeDimensions[R, C])
         (using ValueOf[R], ValueOf[C]):

   lhs =>

   /** Convenience type alias for this Matrix.
     */
   type This = Matrix[R, C, T]

   /** Returns the element at the index specified at runtime.
     *
     * @param rowIdx
     *   row index
     * @param colIdx
     *   column index
     */
   def apply(rowIdx: Int, colIdx: Int): T

   /** Returns the element at the index specified at compile-time.
     *
     * @tparam RowIdx
     *   row index
     * @tparam ColIdx
     *   column index
     */
   def apply[RowIdx <: Int, ColIdx <: Int]
            (using Matrix.Requirements.PositionWithinShape[RowIdx, ColIdx, R, C])
            (using ValueOf[RowIdx], ValueOf[ColIdx])
            : T = apply(valueOf[RowIdx], valueOf[ColIdx])

   /** Returns the row dimension.
     */
   final def rowDim: Int = valueOf[R]

   /** Returns the column dimension.
     */
   final def colDim: Int = valueOf[C]

   /** Checks the equality of this and the other Matrix by performing element-wise comparision.
     *
     * @return
     *   `true` if both Matrices are equal
     */
   final def ===(rhs: This)(using me: MatrixEquality[R, C, T]): Boolean = me.matricesEqual(lhs, rhs)

   /** Performs a Matrix Addition.
     */
   final def +(rhs: This)(using ma: MatrixAddition[R, C, T]): This = ma.plus(lhs, rhs)

   /** Performs a Matrix Subtraction.
     */
   final def -(rhs: This)(using ms: MatrixSubtraction[R, C, T]): This = ms.minus(lhs, rhs)

   /** Performs a Matrix Multiplication by calculating the dot product.
     */
   final infix def dot[L <: Int, U, X]
            (rhs: Matrix[C, L, U])
            (using mm: MatrixMultiplication[R, C, L, T, U, X])
            : mm.Out = mm.dot(lhs, rhs)

   /** Returns the transposed Matrix.
     */
   final def transpose(using mt: Transpose[R, C, T]): Matrix[C, R, T] = mt.transpose(lhs)

   /** Returns the submatrix with the specified bounds.
     *
     * @tparam RowIdxTL
     *   row index of the top-left vertex
     * @tparam ColIdxTL
     *   column index of the top-left vertex
     * @tparam RowIdxBR
     *   row index of the bottom-right vertex
     * @tparam ColIdxBR
     *   column index of the bottom-right vertex
     */
   final def submatrix[RowIdxTL <: Int, ColIdxTL <: Int, RowIdxBR <: Int, ColIdxBR <: Int]
            (using sub: Submatrix[RowIdxTL, ColIdxTL, RowIdxBR, ColIdxBR, R, C, T])
            : Matrix[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1, T] = sub.submatrix(this)

   /** Returns the determinant of this Matrix. Requires that this Matrix is a squared Matrix.
     */
   final def det(using d: Determinant[R, C, T]): T = d.det(this)

   /** Returns the inverse of this Matrix. Requires that this Matrix is a squared Matrix.
     */
   final def inv(using i: Inverse[R, C, T]): This = i.inv(this)

   /** Renders this Matrix to a String using the elements' `toString` method.
     */
   def mkString: String = impl.MkString(this, _.toString)

   /** Renders this Matrix to a String using the specified function.
     */
   def mkString(elemToString: T => String): String = impl.MkString(this, elemToString)

   /** Maps this Matrix to a new Matrix by applying the specified operation element-wise.
     */
   def map[U](op: T => U)(using mf: MatrixFactory[R, C, U]): Matrix[R, C, U] = impl.Map(this, op)

   /** Combines this Matrix and the other Matrix by applying the specified operation on the
     * respective corresponding elements.
     */
   def combine[U, V]
            (rhs: Matrix[R, C, U])
            (op: (T, U) => V)
            (using mf: MatrixFactory[R, C, V])
            : Matrix[R, C, V] = impl.Combine(lhs, rhs, op)

   /** Allows to easily "modify" this Matrix by creating a `Builder` initialized with the values of
     * this Matrix.
     */
   def modify(using mf: MatrixFactory[R, C, T]): Matrix.Builder[R, C, T] = impl.Modify(this)

   /** Iterates over this Matrix by invoking the specified operation with each element's index.
     */
   def iterate(op: (Int, Int) => Unit): Unit = impl.Iterate(this, op)

   /** Folds over the elements of this Matrix by invoking the specified operation with each
     * element's index and the result of the previous invocation.
     *
     * @param start
     *   value provided to the first invocation
     * @param op
     *   operation applied to each element (index)
     * @return
     *   result of the last invocation of the specified operation
     */
   def fold[S](start: S)(op: (S, Int, Int) => S): S = impl.Fold(this, start, op)

object Matrix:

   /** A `Builder` is a "Matrix under construction" that itself forms a (mutable) Matrix. Utilizing
     * `Builder` is the preferred way of creating new Matrices in Matrix operations.
     *
     * Invoking the same `Builder` instance multiple times is OK, but letting a `Builder` leave a
     * local scope is not OK (it's mutable!).
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

   object Requirements:

      /** Validates at compile-time that the specified Matrix dimensions are non-negative.
        */
      type NonNegativeDimensions[R <: Int, C <: Int] = (R > 0 && C > 0) =:= true

      /** Validates at compile-time that the specified Matrix dimensions form a squared Matrix.
        */
      type IsSquare[R <: Int, C <: Int] = R == C =:= true

      /** Validates at compile-time that the specified row and column index are within the given
        * Matrix dimensions.
        */
      type PositionWithinShape[RowIdx <: Int, ColIdx <: Int, R <: Int, C <: Int] =
         (RowIdx >= 0 && RowIdx < R && ColIdx >= 0 && ColIdx < C) =:= true

      /** Validates that the specified row and column index are within the given Matrix dimensions.
        */
      def positionWithinShape(rowIdx: Int, colIdx: Int, rowDim: Int, colDim: Int): Unit = require(
         rowIdx >= 0 && rowIdx < rowDim && colIdx >= 0 && colIdx < colDim,
         s"Given zero-based position ($rowIdx, $colIdx) not within shape ($rowDim, $colDim) of Matrix!"
      )

// TODO move
// type MatrixContext[R <: Int, C <: Int, T] =
//    (ValueOf[R],
//     ValueOf[C],
//     Numeric[T],
//     Matrix.Requirements.NonNegativeDimensions[R, C]
//    ) ?=> Matrix.Builder[R, C, T]
