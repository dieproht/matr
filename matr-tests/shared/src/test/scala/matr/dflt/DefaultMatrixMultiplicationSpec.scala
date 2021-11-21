package matr.dflt

import matr.ArbitraryMatrix
import matr.GenNumericMatrix
import matr.Matrix
import matr.MatrixMultiplication
import matr.MatrixFactory
import matr.MatrFlatSpec
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import org.scalacheck.Arbitrary

class DefaultMatrixMultiplictionSpec extends MatrFlatSpec:

   "Matrix multiplication" should "return dot product of two matrices" in {

      val matrixIntMul = summon[MatrixMultiplication[3, 2, 4, Int, Int, Int]]

      val maxVal = 100

      val gen =
         for
            g1 <- GenNumericMatrix[3, 2, Int](-1 * maxVal, maxVal)
            g2 <- GenNumericMatrix[2, 4, Int](-1 * maxVal, maxVal)
         yield (g1, g2)

      forAll(gen) { (m1: Matrix[3, 2, Int], m2: Matrix[2, 4, Int]) =>
         val resultMul: Matrix[3, 4, Int] = matrixIntMul.dot(m1, m2)

         val resultExp = MatrixFactory[3, 4, Int].rowMajor(
            m1(0, 0) * m2(0, 0) + m1(0, 1) * m2(1, 0),
            m1(0, 0) * m2(0, 1) + m1(0, 1) * m2(1, 1),
            m1(0, 0) * m2(0, 2) + m1(0, 1) * m2(1, 2),
            m1(0, 0) * m2(0, 3) + m1(0, 1) * m2(1, 3),
            m1(1, 0) * m2(0, 0) + m1(1, 1) * m2(1, 0),
            m1(1, 0) * m2(0, 1) + m1(1, 1) * m2(1, 1),
            m1(1, 0) * m2(0, 2) + m1(1, 1) * m2(1, 2),
            m1(1, 0) * m2(0, 3) + m1(1, 1) * m2(1, 3),
            m1(2, 0) * m2(0, 0) + m1(2, 1) * m2(1, 0),
            m1(2, 0) * m2(0, 1) + m1(2, 1) * m2(1, 1),
            m1(2, 0) * m2(0, 2) + m1(2, 1) * m2(1, 2),
            m1(2, 0) * m2(0, 3) + m1(2, 1) * m2(1, 3)
         )

         resultMul === resultExp shouldBe true
      }
   }
