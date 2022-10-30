package matr.dflt

// import matr.ArbitraryMatrix
import matr.GenNumericMatrix
import matr.Inverse
import matr.MatrFlatSpec
import matr.Matrix
import matr.MatrixFactory
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import org.scalacheck.Gen

import scala.math
import scala.util.Random

import math.Numeric.Implicits.infixNumericOps

class DefaultInverseSpec extends MatrFlatSpec:

   "DefaultInverse" should "return correct inverse for 3x3 Matrix" in {

      val num = Numeric[BigDecimal]

      val id = MatrixFactory[3, 3, BigDecimal].identity

      val gen = GenNumericMatrix[3, 3, Int](1, 100).map { m =>
         m.map { vint =>
            val neg = Random.nextBoolean
            val vdec = BigDecimal(vint)
            if neg then
               vdec * -1
            else
               vdec
         }
      }

      forAll(gen) { (m1: Matrix[3, 3, BigDecimal]) =>
         try
            val mInv = m1.inv
            val m2 = m1 dot mInv
            matricesEqualApproximately(id, m2) shouldEqual true
         catch
            case MatrixNotInvertibleException() =>
               succeed
      }
   }

   private def matricesEqualApproximately[T, M <: Matrix[? <: Int, ? <: Int, T]]
            (lhs: M, rhs: M)
            (using Numeric[T])
            : Boolean =
      val epsilon = 0.001
      lhs.fold(true) { (curr, rowIdx, colIdx) =>
         curr && math.abs(lhs(rowIdx, colIdx).toDouble - rhs(rowIdx, colIdx).toDouble) < epsilon
      }
