package matr.dflt

import matr.ArbitraryMatrix
import matr.Matrix
import matr.MatrixEquality
import matr.MatrFlatSpec
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import org.scalacheck.Gen
import org.scalacheck.Arbitrary

class DefaultMatrixEqualitySpec extends MatrFlatSpec:

   "A matrix" should "equal itself" in {

      val matrixEquality = summon[MatrixEquality[2, 3, Int]]

      given Arbitrary[Matrix[2, 3, Int]] = ArbitraryMatrix[2, 3, Int]
      forAll { (m: Matrix[2, 3, Int]) =>
         matrixEquality.matricesEqual(m, m) shouldBe true
      }
   }

   "Two matrices" should "not equal when they are completely different" in {

      val matrixEquality = summon[MatrixEquality[2, 3, Int]]

      given Arbitrary[Matrix[2, 3, Int]] = ArbitraryMatrix[2, 3, Int]

      forAll { (m1: Matrix[2, 3, Int], m2: Matrix[2, 3, Int]) =>
         matrixEquality.matricesEqual(m1, m2) shouldBe false
      }
   }

   it should "not equal when they differ in one element" in {

      val matrixEquality = summon[MatrixEquality[2, 3, Int]]

      given Arbitrary[Matrix[2, 3, Int]] = ArbitraryMatrix[2, 3, Int]

      forAll { (m1: Matrix[2, 3, Int]) =>
         val m2Buildr = m1.modify
         m2Buildr(0, 1) = m1(0, 1) + 7
         val m2 = m2Buildr.result
         matrixEquality.matricesEqual(m1, m2) shouldBe false
      }
   }
