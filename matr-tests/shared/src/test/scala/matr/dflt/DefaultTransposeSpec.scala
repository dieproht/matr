package matr.dflt

import matr.ArbitraryMatrix
import matr.MatrFlatSpec
import matr.Matrix
import matr.MatrixFactory
import matr.Transpose
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import org.scalacheck.Arbitrary

class DefaultTransposeSpec extends MatrFlatSpec:

   "Transpose" should "return transposed Matrix" in:

      val transpose: Transpose[2, 3, Int] = summon[Transpose[2, 3, Int]]

      given Arbitrary[Matrix[2, 3, Int]] = ArbitraryMatrix[2, 3, Int]

      forAll { (m: Matrix[2, 3, Int]) =>
         val resExp = MatrixFactory[3, 2, Int].rowMajor(
            m(0, 0),
            m(1, 0),
            m(0, 1),
            m(1, 1),
            m(0, 2),
            m(1, 2)
         )

         val resTranspose = transpose.transpose(m)

         resTranspose === resExp shouldBe true
      }

   it should "return original Matrix when transposing transposed Matrix" in:

      val transpose1: Transpose[2, 3, Int] = summon[Transpose[2, 3, Int]]
      val transpose2: Transpose[3, 2, Int] = summon[Transpose[3, 2, Int]]

      given Arbitrary[Matrix[2, 3, Int]] = ArbitraryMatrix[2, 3, Int]

      forAll { (m: Matrix[2, 3, Int]) =>
         val resTransposeTranspose = transpose2.transpose(transpose1.transpose(m))

         resTransposeTranspose === m shouldBe true
      }
