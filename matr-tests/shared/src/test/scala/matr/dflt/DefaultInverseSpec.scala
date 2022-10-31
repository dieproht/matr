package matr.dflt

import matr.GenNumericMatrix
import matr.Inverse
import matr.MatrFlatSpec
import matr.Matrix
import matr.MatrixFactory
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import org.scalacheck.Gen
import matr.MatrixNotInvertibleException

import scala.math
import scala.util.Random

import math.Numeric.Implicits.infixNumericOps

class DefaultInverseSpec extends MatrFlatSpec:

   "DefaultInverse" should "return correct inverse for 1x1 Matrix" in testInverse[1]

   "DefaultInverse" should "return correct inverse for 2x2 Matrix" in testInverse[2]

   "DefaultInverse" should "return correct inverse for 3x3 Matrix" in testInverse[3]

   "DefaultInverse" should "return correct inverse for 4x4 Matrix" in testInverse[4]

   private def testInverse[N <: Int]
            (using Matrix.Requirements.NonNegativeDimensions[N, N])
            (using MatrixFactory[N, N, BigDecimal])
            (using ValueOf[N])
            (using Matrix.Requirements.IsSquare[N, N]) =
      val num = Numeric[BigDecimal]

      val id = MatrixFactory[N, N, BigDecimal].identity

      val gen = GenNumericMatrix[N, N, Int](1, 100).map { m =>
         m.map { vint =>
            val neg = Random.nextBoolean
            val vdec = BigDecimal(vint)
            if neg then
               vdec * -1
            else
               vdec
         }
      }

      forAll(gen) { (m1: Matrix[N, N, BigDecimal]) =>
         try
            val mInv = m1.inv
            val m2 = m1 dot mInv
            matricesEqualApproximately(id, m2) shouldEqual true
         catch
            case MatrixNotInvertibleException() =>
               succeed
      }

   private def matricesEqualApproximately[T, M <: Matrix[? <: Int, ? <: Int, T]]
            (lhs: M, rhs: M)
            (using Numeric[T])
            : Boolean =
      val epsilon = 0.001
      lhs.fold(true) { (curr, rowIdx, colIdx) =>
         curr && math.abs(lhs(rowIdx, colIdx).toDouble - rhs(rowIdx, colIdx).toDouble) < epsilon
      }
