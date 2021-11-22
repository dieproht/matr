package matr

import matr.Matrix
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import scala.util.Random
import org.scalacheck.Arbitrary

class MatrixCombineSpec extends MatrFlatSpec:

   "Combining two Matrices" should "require matrices with same shape" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double]
         .tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[4, 4, Double] = MatrixFactory[4, 4, Double]
         .tabulate((_, _) => Random.nextDouble)
      val fn: (Double, Double) => Int = (a, b) => (a + b).toInt
      assertTypeError("val res = m1.combine(m2)(fn)")
   }

   it should
      "require a function that takes the data type of the elements (left-hand side as first argument)" in {
         val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double]
            .tabulate((_, _) => Random.nextDouble)
         val m2: Matrix[2, 3, Float] = MatrixFactory[2, 3, Float]
            .tabulate((_, _) => Random.nextFloat)
         val fn: (Float, Float) => Int = (a, b) => (a + b).toInt
         assertTypeError("val res = m1.combine(m2)(fn)")
      }

   it should
      "require a function that takes the data type of the elements (right-hand side as second argument)" in {
         val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double]
            .tabulate((_, _) => Random.nextDouble)
         val m2: Matrix[2, 3, Float] = MatrixFactory[2, 3, Float]
            .tabulate((_, _) => Random.nextFloat)
         val fn: (Double, Double) => Int = (a, b) => (a + b).toInt
         assertTypeError("val res = m1.combine(m2)(fn)")
      }

   it should
      "require a function that returns the data type of Matrix the result is assigned to" in {
         val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double]
            .tabulate((_, _) => Random.nextDouble)
         val m2: Matrix[2, 3, Float] = MatrixFactory[2, 3, Float]
            .tabulate((_, _) => Random.nextFloat)
         val fn: (Double, Double) => Int = (a, b) => (a + b).toInt

         assertTypeError("val res: Matrix[2, 3, Float] = m1.combine(m2)(fn)")
      }

   it should
      "return the left-hand side Matrix when applying the identity function on the left-hand side" in {

         given Arbitrary[Matrix[6, 4, Double]] = ArbitraryMatrix[6, 4, Double]

         forAll { (mLhs: Matrix[6, 4, Double], mRhs: Matrix[6, 4, Double]) =>
            val fn: (Double, Double) => Double = (lhs, _) => lhs
            val mRes = mLhs.combine(mRhs)(fn)
            mRes === mLhs shouldBe true
         }
      }

   it should
      "return the right-hand side Matrix when applying the identity function on the right-hand side" in {

         given Arbitrary[Matrix[6, 4, Double]] = ArbitraryMatrix[6, 4, Double]

         forAll { (mLhs: Matrix[6, 4, Double], mRhs: Matrix[6, 4, Double]) =>
            val fn: (Double, Double) => Double = (_, rhs) => rhs
            val mRes = mLhs.combine(mRhs)(fn)
            mRes === mRhs shouldBe true
         }
      }

   it should "apply given function element-wise on the given Matrices" in {

      given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

      forAll { (mLhs: Matrix[3, 2, Int], mRhs: Matrix[3, 2, Int]) =>
         val fn: (Int, Int) => Int = (lhs, rhs) => scala.math.max(lhs, rhs) - 10

         val mRes: Matrix[3, 2, Int] = mLhs.combine(mRhs)(fn)

         val mExp: Matrix[3, 2, Int] = MatrixFactory[3, 2, Int].rowMajor(
            fn(mLhs(0, 0), mRhs(0, 0)),
            fn(mLhs(0, 1), mRhs(0, 1)),
            fn(mLhs(1, 0), mRhs(1, 0)),
            fn(mLhs(1, 1), mRhs(1, 1)),
            fn(mLhs(2, 0), mRhs(2, 0)),
            fn(mLhs(2, 1), mRhs(2, 1))
         )

         mRes === mExp shouldBe true
      }
   }
