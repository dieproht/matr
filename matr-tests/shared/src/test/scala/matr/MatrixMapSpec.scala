package matr

import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import org.scalacheck.Arbitrary

import scala.annotation.nowarn
import scala.util.Random

@nowarn
class MatrixMapSpec extends MatrFlatSpec:

   "Mapping a Matrix" should "require a function that takes the data type of the elements" in:

      val m: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) =>
         Random.nextDouble
      )

      val fn: Int => Double = _.toDouble

      assertTypeError("val res = m.map(fn)")

   it should "return the same Matrix when applying the identity function" in:

      given Arbitrary[Matrix[6, 4, Double]] = ArbitraryMatrix[6, 4, Double]

      forAll { (m: Matrix[6, 4, Double]) =>
         val mRes = m.map(identity)
         mRes === m shouldBe true
      }

   it should "return Matrix of zeros when mapping by a function always returning zeros" in:

      given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

      forAll { (m: Matrix[3, 2, Int]) =>
         val fn: Int => Int = _ => 0
         val mRes: Matrix[3, 2, Int] = m.map(fn)
         val z: Matrix[3, 2, Int] = MatrixFactory[3, 2, Int].zeros
         mRes === z shouldBe true
      }

   it should "apply given function element-wise on the Matrix" in:

      given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

      forAll { (m: Matrix[3, 2, Int]) =>
         val fn: Int => Int = _ * 2

         val mRes: Matrix[3, 2, Int] = m.map(fn)

         val mExp: Matrix[3, 2, Int] = MatrixFactory[3, 2, Int].rowMajor(
            fn(m(0, 0)),
            fn(m(0, 1)),
            fn(m(1, 0)),
            fn(m(1, 1)),
            fn(m(2, 0)),
            fn(m(2, 1))
         )

         mRes === mExp shouldBe true
      }
