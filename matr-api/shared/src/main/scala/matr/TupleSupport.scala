package matr

/** Auxiliary traits and givens supporting tuple-based construction of Matrices via
  * `MatrixFactory.fromTuple`.
  */
trait TupleSupport:

   trait RowTupleReader[RowTuple, T]:
      def readRow
         (rowIdx: Int,
          rowTuple: RowTuple,
          colDim: Int,
          setElem: (rowIdx: Int, colIdx: Int, elem: T) => Unit
         )
         : Unit

   given emptyRowTupleReader[T]: RowTupleReader[EmptyTuple, T] with
      def readRow
               (rowIdx: Int,
                rowTuple: EmptyTuple,
                colDim: Int,
                setElem: (rowIdx: Int, colIdx: Int, elem: T) => Unit
               )
               : Unit = ()

   given inductiveRowTupleReader[Tail <: Tuple, T]
            (using tailReader: RowTupleReader[Tail, T])
            : RowTupleReader[T *: Tail, T] with
      def readRow
               (rowIdx: Int,
                rowTuple: T *: Tail,
                colDim: Int,
                setElem: (rowIdx: Int, colIdx: Int, elem: T) => Unit
               )
               : Unit =
         val curElem: T = rowTuple.head
         val remainingRow: Tail = rowTuple.tail
         val colIdx: Int = colDim - remainingRow.size - 1
         setElem(rowIdx, colIdx, curElem)
         tailReader.readRow(rowIdx, remainingRow, colDim, setElem)

   trait MatrixTupleReader[MatrixTuple, RowTuple]:
      def readMatrix(m: MatrixTuple, rowDim: Int, setRow: (rowIdx: Int, rowTuple: RowTuple) => Unit)
         : Unit

   given emptyMatrixTupleReader[RowTuple]: MatrixTupleReader[EmptyTuple, RowTuple] with
      def readMatrix
               (m: EmptyTuple, rowDim: Int, setRow: (rowIdx: Int, rowTuple: RowTuple) => Unit)
               : Unit = ()

   given inductiveMatrixTupleReader[MatrixTail <: Tuple, RowTuple]
            (using matrixTailReader: MatrixTupleReader[MatrixTail, RowTuple])
            : MatrixTupleReader[RowTuple *: MatrixTail, RowTuple] with
      def readMatrix
               (m: RowTuple *: MatrixTail,
                rowDim: Int,
                setRow: (rowIdx: Int, rowTuple: RowTuple) => Unit
               )
               : Unit =
         val curRow: RowTuple = m.head
         val remainingMatrix: MatrixTail = m.tail
         val rowIdx: Int = rowDim - remainingMatrix.size - 1
         setRow(rowIdx, curRow)
         matrixTailReader.readMatrix(remainingMatrix, rowDim, setRow)

object TupleSupport extends TupleSupport
