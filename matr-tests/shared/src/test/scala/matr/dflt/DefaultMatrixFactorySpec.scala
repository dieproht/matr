package test.matr.dflt

import matr.ArbitraryMatrix
import matr.GenMatrix
import matr.GenNumericMatrix
import matr.Matrix
import matr.MatrixFactory
import matr.MatrFlatSpec
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.dflt.DefaultDenseMatrix
import matr.dflt.DefaultSparseMatrix
import matr.std.StandardOps.given
import matr.util.RowMajorIndex
import scala.util.Random
import org.scalacheck.Arbitrary
import org.scalacheck.Gen

class DefaultMatrixFactorySpec extends MatrFlatSpec:

   "Builder-built Matrix" should "equal given Matrix 1x1" in {

      val gen = Gen.containerOfN[Array, Long](1, Arbitrary.arbitrary[Long])

      forAll(gen) { (elements: Array[Long]) =>
         val buildr = MatrixFactory[1, 1, Long].builder
         buildr(0, 0) = elements(0)
         val mRes: Matrix[1, 1, Long] = buildr.result
         val mExp: Matrix[1, 1, Long] = DefaultDenseMatrix(elements)
         mRes === mExp shouldBe true
      }
   }

   it should "equal given Matrix 1x2" in {

      val gen = Gen.containerOfN[Array, Long](2, Arbitrary.arbitrary[Long])

      forAll(gen) { (elements: Array[Long]) =>
         val buildr = MatrixFactory[1, 2, Long].builder
         buildr(0, 0) = elements(0)
         buildr(0, 1) = elements(1)
         val mRes: Matrix[1, 2, Long] = buildr.result
         val mExp: Matrix[1, 2, Long] = DefaultDenseMatrix(elements)
         mRes === mExp shouldBe true
      }
   }

   it should "equal given Matrix 2x1" in {

      val gen = Gen.containerOfN[Array, Long](2, Arbitrary.arbitrary[Long])

      forAll(gen) { (elements: Array[Long]) =>
         val buildr = MatrixFactory[2, 1, Long].builder
         buildr(0, 0) = elements(0)
         buildr(1, 0) = elements(1)
         val mRes: Matrix[2, 1, Long] = buildr.result
         val mExp: Matrix[2, 1, Long] = DefaultDenseMatrix(elements)
         mRes === mExp shouldBe true
      }
   }

   it should "equal given Matrix 2x2" in {

      val gen = Gen.containerOfN[Array, Long](4, Arbitrary.arbitrary[Long])

      forAll(gen) { (elements: Array[Long]) =>
         val buildr = MatrixFactory[2, 2, Long].builder

         for
            rowIdx <- 0 to 1
            colIdx <- 0 to 1
         do buildr(rowIdx, colIdx) = elements(RowMajorIndex.toIdx(rowIdx, colIdx, 2))

         val mRes: Matrix[2, 2, Long] = buildr.result
         val mExp: Matrix[2, 2, Long] = DefaultDenseMatrix(elements)

         mRes === mExp shouldBe true
      }
   }

   it should "equal given Matrix 4x7" in {

      val gen = Gen.containerOfN[Array, Long](28, Arbitrary.arbitrary[Long])

      forAll(gen) { (elements: Array[Long]) =>
         val buildr = MatrixFactory[4, 7, Long].builder

         for
            rowIdx <- 0 to 3
            colIdx <- 0 to 6
         do buildr(rowIdx, colIdx) = elements(RowMajorIndex.toIdx(rowIdx, colIdx, 7))

         val mRes: Matrix[4, 7, Long] = buildr.result
         val mExp: Matrix[4, 7, Long] = DefaultDenseMatrix(elements)

         mRes === mExp shouldBe true
      }
   }

   it should "be a SparseMatrix when containing only zeros" in {

      val m: Matrix[3, 3, Long] = MatrixFactory[3, 3, Long].zeros

      m shouldBe a[DefaultSparseMatrix[3, 3, Long]]
   }

   it should "be a DenseMatrix when containing no zeros" in {

      val m: Matrix[3, 3, Long] = MatrixFactory[3, 3, Long].ones

      m shouldBe a[DefaultDenseMatrix[3, 3, Long]]
   }

   it should "be a SparseMatrix when containing more zeros than treshold" in {

      val num: Numeric[Long] = summon[Numeric[Long]]

      val _0 = num.zero
      val _1 = num.one

      val m: Matrix[3, 3, Long] =
         MatrixFactory[3, 3, Long].rowMajor(_0, _1, _0, _1, _0, _0, _1, _0, _1)

      m shouldBe a[DefaultSparseMatrix[3, 3, Long]]
   }

   it should "be a DenseMatrix when containing less zeros than treshold" in {

      val num: Numeric[Long] = summon[Numeric[Long]]

      val _0 = num.zero
      val _1 = num.one

      val m: Matrix[3, 3, Long] =
         MatrixFactory[3, 3, Long].rowMajor(_0, _1, _0, _1, _1, _0, _1, _0, _1)

      m shouldBe a[DefaultDenseMatrix[3, 3, Long]]
   }

   "A Builder" should "return zero for all indices when not yet updated" in {

      val num: Numeric[Int] = summon[Numeric[Int]]
      val buildr = MatrixFactory[4, 7, Int].builder

      for
         rowIdx <- 0 to 3
         colIdx <- 0 to 6
      do buildr(rowIdx, colIdx) shouldEqual num.zero
   }

   it should "return assigned values" in {

      val gen = Gen.containerOfN[Array, Long](28, Arbitrary.arbitrary[Long])

      forAll(gen) { (elements: Array[Long]) =>
         val buildr = MatrixFactory[4, 7, Long].builder

         for
            rowIdx <- 0 to 3
            colIdx <- 0 to 6
         do buildr(rowIdx, colIdx) = elements(RowMajorIndex.toIdx(rowIdx, colIdx, 7))

         val mExp: Matrix[4, 7, Long] = DefaultDenseMatrix(elements)

         for
            rowIdx <- 0 to 3
            colIdx <- 0 to 6
         do buildr(rowIdx, colIdx) shouldEqual mExp(rowIdx, colIdx)
      }
   }

   "DefaultMatrixFactory.rowMajor" should "raise an error at runtime when given element collection is too big" in {
      assertThrows[IllegalArgumentException] {
         MatrixFactory[2, 2, Int].rowMajor(1, 2, 3, 4, 5)
      }
   }

   it should "raise an error at runtime when given element collection is too small" in {
      assertThrows[IllegalArgumentException] {
         MatrixFactory[2, 2, Int].rowMajor(1, 2, 3)
      }
   }

   it should "create a SparseMatrix with the given elements" in {

      val num: Numeric[Long] = summon[Numeric[Long]]

      val _0 = num.zero
      def _n = Random.nextLong

      val elements = Seq(_0, _n, _0, _n, _n, _0, _0, _0, _n)

      val m: Matrix[3, 3, Long] = MatrixFactory[3, 3, Long].rowMajor(elements*)

      for
         rowIdx <- 0 to 2
         colIdx <- 0 to 2
      do m(rowIdx, colIdx) shouldEqual elements(RowMajorIndex.toIdx(rowIdx, colIdx, 3))
   }

   it should "create a DenseMatrix with the given elements" in {

      val num: Numeric[Long] = summon[Numeric[Long]]

      val _0 = num.zero
      def _n = Random.nextLong

      val elements = Seq(_0, _n, _0, _n, _n, _0, _n, _n, _n)

      val m: Matrix[3, 3, Long] = MatrixFactory[3, 3, Long].rowMajor(elements*)

      for
         rowIdx <- 0 to 2
         colIdx <- 0 to 2
      do m(rowIdx, colIdx) shouldEqual elements(RowMajorIndex.toIdx(rowIdx, colIdx, 3))
   }
