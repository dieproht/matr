package matr.dsl2

import scala.collection.mutable
import scala.compiletime.ops.any.==
import scala.compiletime.ops.boolean.||
import scala.compiletime.ops.int.{+, <, >}
import matr.Matrix
import matr.MatrixFactory
import scala.Tuple.Union

trait RowTupleReader[RowTuple, T]:
     def readRow(row: RowTuple, colDim: Int, setElem: (colIdx: Int, elem: T) => Unit): Unit
end RowTupleReader

given emptyRowTupleReader[T]: RowTupleReader[EmptyTuple, T] with
     def readRow(row: EmptyTuple, colDim: Int, setElem: (colIdx: Int, elem: T) => Unit): Unit = ()

given inductiveRowTupleReader[T, Tail <: Tuple](using
    tailReader: RowTupleReader[Tail, T]
): RowTupleReader[T *: Tail, T] with
     def readRow(tuple: T *: Tail, colDim: Int, setElem: (colIdx: Int, elem: T) => Unit): Unit =
          val curElem: T         = tuple.head
          val remainingRow: Tail = tuple.tail
          val colIdx: Int        = colDim - remainingRow.size - 1
          setElem(colIdx, curElem)
          tailReader.readRow(remainingRow, colDim, setElem)

/*
 *  M[2, 3, Int]
 *       || (1, 2, 3)
 *       || (4, 5, 6)
 *       || $
 *
 */
object M:
     def apply[R <: Int, C <: Int, T](using mf: MatrixFactory[R, C, T]) =
          Buildr[0, R, C, T](mf.builder)

// val || = M

case class Buildr[CurRow <: Int, R <: Int, C <: Int, T](mfb: MatrixFactory.Builder[R, C, T]):

     def ||[RowType <: Tuple](row: RowType)(using CurRow < R =:= true)(using
         Tuple.Size[RowType] =:= C
     )(using Tuple.Union[RowType] =:= T)(using
         curRow: ValueOf[CurRow],
         vC: ValueOf[C]
     )(using reader: RowTupleReader[RowType, T]): Buildr[CurRow + 1, R, C, T] =
          val setter: (Int, T) => Unit = mfb.update(curRow.value, _, _)
          reader.readRow(row, vC.value, setter)
          Buildr(mfb)
     def ||(eom: EndOfMatrix)(using CurRow == R =:= true): Matrix[R, C, T] = mfb.result

end Buildr

sealed trait EndOfMatrix
object EndOfMatrix extends EndOfMatrix
val $ = EndOfMatrix
