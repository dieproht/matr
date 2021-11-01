package matr.dsl

import scala.collection.mutable
import scala.compiletime.ops.any.==
import scala.compiletime.ops.boolean.||
import scala.compiletime.ops.int.{+, <, >}
import matr.Matrix
import matr.MatrixFactory

object M:

   def apply[R <: Int, C <: Int, T]
         (using vr: ValueOf[R], vc: ValueOf[C])
         (using Matrix.DimsOK[R, C] =:= true)
         (using mf: MatrixFactory[R, C, T])
         : DslBuilder[0, -1, R, C, T] =
      DslBuilder(mf.builder)

val || = M

case class DslBuilder[CurRow <: Int, CurCol <: Int, R <: Int, C <: Int, T] private[dsl]
      (private val builder: MatrixFactory.Builder[R, C, T])
      (using vCurRow: ValueOf[CurRow], vCurCol: ValueOf[CurCol]):

   def ||(v: T)(using CurRow < R =:= true): DslBuilder[CurRow, 0, R, C, T] =
      DslBuilder(builder(vCurRow.value, 0) = v)

   def |
         (v: T)
         (using vCurColSucc: ValueOf[CurCol + 1])
         (using CurCol + 1 < C =:= true)
         : DslBuilder[CurRow, CurCol + 1, R, C, T] =
      DslBuilder(builder(vCurRow.value, vCurColSucc.value) = v)

   def ||
         (end: EndOfLine)
         (using vCurRowSucc: ValueOf[CurRow + 1])
         (using CurRow + 1 < R =:= true)
         (using CurCol + 1 == C =:= true)
         : DslBuilder[CurRow + 1, -1, R, C, T] = DslBuilder(builder)

   def ||
         (end: EndOfMatrix)
         (using CurRow + 1 == R =:= true)
         (using CurCol + 1 == C =:= true)
         : Matrix[R, C, T] =
      builder.result

trait EndOfLine
object EndOfLine extends EndOfLine
val $ = EndOfLine

trait EndOfMatrix
object EndOfMatrix extends EndOfMatrix
val $$ = EndOfMatrix
