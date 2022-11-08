package matr.dflt

import matr.ElementDivision
import matr.ElementMultiplication
import matr.GenNumericMatrix
import matr.Inverse
import matr.MatrFlatSpec
import matr.Matrix
import matr.MatrixFactory
import matr.MatrixNotInvertibleException
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import org.scalacheck.Gen
import spire.compat.numeric
import spire.math.Rational

import scala.math
import scala.util.Random

import math.Numeric.Implicits.infixNumericOps

class DefaultInverseSpec extends MatrFlatSpec:

   given rationalMultiplication: ElementMultiplication[Rational, Rational] with
      def times(lhs: Rational, rhs: Rational): Out = lhs * rhs
      type Out = Rational

   given rationalDivision: ElementDivision[Rational, Rational] with
      def div(lhs: Rational, rhs: Rational): Out = lhs / rhs
      type Out = Rational

   "DefaultInverse" should "return correct inverse for 1x1 Matrix" in testInverse[1]

   it should "return correct inverse for 2x2 Matrix" in testInverse[2]

   it should "return correct inverse for 3x3 Matrix" in testInverse[3]

   it should "return correct inverse for 4x4 Matrix" in testInverse[4]

   private def testInverse[N <: Int]
            (using Matrix.Requirements.NonNegativeDimensions[N, N])
            (using MatrixFactory[N, N, Rational])
            (using ValueOf[N])
            (using Matrix.Requirements.IsSquare[N, N]) =

      val num = Numeric[Rational]

      val id = MatrixFactory[N, N, Rational].identity

      val gen = GenNumericMatrix[N, N, Int](1, 10).map { m =>
         m.map { vint =>
            val neg = Random.nextBoolean
            if neg then
               Rational(-1 * vint, 1)
            else
               Rational(vint, 1)
         }
      }

      forAll(gen) { (m1: Matrix[N, N, Rational]) =>
         try
            val mInv = m1.inv
            val m2 = m1 dot mInv
            (m2 === id) shouldEqual true
         catch
            case MatrixNotInvertibleException() =>
               succeed
      }
