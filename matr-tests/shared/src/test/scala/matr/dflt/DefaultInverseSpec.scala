package matr.dflt

import matr.MatrFlatSpec
import matr.Inverse

import scala.math
import matr.Matrix
import matr.MatrixFactory
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import scala.collection.immutable.Range.BigDecimal.apply
import matr.MatrixEquality

class DefaultInverseSpec extends MatrFlatSpec:

   "DefaultInverse" should "return correct inverse for 3x3 Matrix" in {

      val inverse: Inverse[3, 3, BigDecimal] = summon[Inverse[3, 3, BigDecimal]]

      val m = MatrixFactory[3, 3, BigDecimal].fromTuple( //
         (bd(3), bd(0), bd(2)), //
         (bd(2), bd(1), bd(-2)),
         (bd(0), bd(1), bd(1))
      )

      val inv = m.inv

      val m2 = m dot inv

      // println(inv.mkString)
      println(m2.mkString)

   }

   it should "return correct inverse for 3x3 Matrix (2)" in {

      val inverse: Inverse[3, 3, BigDecimal] = summon[Inverse[3, 3, BigDecimal]]

      val m = MatrixFactory[3, 3, BigDecimal].fromTuple( //
         (bd(1), bd(2), bd(3)), //
         (bd(3), bd(2), bd(1)),
         (bd(2), bd(1), bd(3))
      )

      val inv = m.inv

      val m2 = m dot inv

      val id = MatrixFactory[3, 3, BigDecimal].identity

      // println(inv.mkString)
      println(m2.mkString)

      (m2.===(id)(using DefaultInverseSpec.bdeq)) shouldEqual true

      /*

      (-0.416667  | 0.25  | 0.333333
        0.583333  | 0.25  | -0.666667
        0.0833333 | -0.25 | 0.333333)

       */
   }

   private def bd(v: Int) = BigDecimal(v)

object DefaultInverseSpec:
   // given [R <: Int, C <: Int, T](given Numeric[T]): MatrixEquality[R, C, T] with
   //    val epsilon = 0.001d
   //    val num = summon[Numeric[T]]
   //    def matricesEqual(lhs: M, rhs: M): Boolean =
   //       lhs.fold(true) { (curr, rowIdx, colIdx) =>
   //          curr && math.abs(lhs(rowIdx, colIdx) - rhs(rowIdx, colIdx)).toDouble < epsilon
   //       }
   given bdeq: MatrixEquality[3, 3, BigDecimal] with
      val epsilon = 0.001d
      def matricesEqual(lhs: M, rhs: M): Boolean =
         lhs.fold(true) { (curr, rowIdx, colIdx) =>
            println("comp")
            curr && math.abs(lhs(rowIdx, colIdx).toDouble - rhs(rowIdx, colIdx).toDouble) < epsilon
         }
