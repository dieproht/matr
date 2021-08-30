package matr.dsl3

import scala.collection.mutable
import scala.compiletime.ops.any.==
import scala.compiletime.ops.boolean.||
import scala.compiletime.ops.int.{+, <, >}
import matr.Matrix
import matr.MatrixFactory
import scala.Tuple.Union

trait MatrixTupleReader[MatrixTuple, RowTuple]:
     def readMatrix(m: MatrixTuple, rowDim: Int, setRow: (rowIdx: Int, row: RowTuple) => Unit): Unit

given emptyMatrixTupleReader[RowTuple]: MatrixTupleReader[EmptyTuple, RowTuple] with
     def readMatrix(
         m: EmptyTuple,
         rowDim: Int,
         setRow: (rowIdx: Int, row: RowTuple) => Unit
     ): Unit = ()

given inductiveMatrixTupleReader[MatrixTail <: Tuple, RowTuple](using
    matrixTailReader: MatrixTupleReader[MatrixTail, RowTuple]
): MatrixTupleReader[RowTuple *: MatrixTail, RowTuple] with
     def readMatrix(
         m: RowTuple *: MatrixTail,
         rowDim: Int,
         setRow: (rowIdx: Int, row: RowTuple) => Unit
     ): Unit =
          val curRow: RowTuple            = m.head
          val remainingMatrix: MatrixTail = m.tail
          val rowIdx: Int                 = rowDim - remainingMatrix.size - 1
          setRow(rowIdx, curRow)
          matrixTailReader.readMatrix(remainingMatrix, rowDim, setRow)

trait RowTupleReader[RowTuple, T]:
     def readRow(
         rowIdx: Int,
         row: RowTuple,
         colDim: Int,
         setElem: (Int, Int, T) => Unit
     ): Unit

given emptyRowTupleReader[T]: RowTupleReader[EmptyTuple, T] with
     def readRow(
         rowIdx: Int,
         row: EmptyTuple,
         colDim: Int,
         setElem: (Int, Int, T) => Unit
     ): Unit = ()

given inductiveRowTupleReader[Tail <: Tuple, T](using
    tailReader: RowTupleReader[Tail, T]
): RowTupleReader[T *: Tail, T] with
     def readRow(
         rowIdx: Int,
         tuple: T *: Tail,
         colDim: Int,
         setElem: (Int, Int, T) => Unit
     ): Unit =
          val curElem: T         = tuple.head
          val remainingRow: Tail = tuple.tail
          val colIdx: Int        = colDim - remainingRow.size - 1
          setElem(rowIdx, colIdx, curElem)
          tailReader.readRow(rowIdx, remainingRow, colDim, setElem)

/*
 *  M[2, 3, Int] mk
 *       ((1, 2, 3),
 *       (4, 5, 6))
 */
class M[R <: Int, C <: Int, T](using mf: MatrixFactory[R, C, T]):

     private val builder: MatrixFactory.Builder[R, C, T] = mf.builder

     infix def mk[MatrixTuple <: Tuple, RowTuple <: Tuple](
         matrixTuple: MatrixTuple
     )(using vR: ValueOf[R], vC: ValueOf[C])(using
         mReader: MatrixTupleReader[MatrixTuple, RowTuple],
         rowReader: RowTupleReader[RowTuple, T]
     )(using Tuple.Size[MatrixTuple] =:= R, Tuple.Size[RowTuple] =:= C): Matrix[R, C, T] =
          val setElem = (rowIdx: Int, colIdx: Int, elem: T) =>
               builder.update(rowIdx, colIdx, elem)
               ()
          val setRow = (rowIdx: Int, row: RowTuple) =>
               rowReader.readRow(rowIdx, row, vC.value, setElem)
          mReader.readMatrix(matrixTuple, vR.value, setRow)
          builder.result
