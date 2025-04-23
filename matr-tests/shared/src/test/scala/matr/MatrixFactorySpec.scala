package test.matr

import matr.ArbitraryMatrix
import matr.MatrFlatSpec
import matr.Matrix
import matr.MatrixFactory
import matr.TupleSupport.given
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import org.scalacheck.Arbitrary

class MatrixFactorySpec extends MatrFlatSpec:

   "Tuple-built Matrix" should "equal row-major built Matrix" in:
      val m1: Matrix[4, 3, Int] = MatrixFactory[4, 3, Int].rowMajor(
         1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
      )
      val m2: Matrix[4, 3, Int] = MatrixFactory[4, 3, Int].fromTuple(
         (1, 2, 3),
         (4, 5, 6),
         (7, 8, 9),
         (10, 11, 12)
      )
      m1 === m2 shouldEqual true

   it should "equal given Matrix 3x2" in:

      given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

      forAll { (mg: Matrix[3, 2, Int]) =>

         val md: Matrix[3, 2, Int] = MatrixFactory[3, 2, Int].fromTuple(
            (mg(0, 0), mg(0, 1)),
            (mg(1, 0), mg(1, 1)),
            (mg(2, 0), mg(2, 1))
         )

         md === mg shouldBe true
      }

   it should "equal given Matrix 3x4" in:

      given Arbitrary[Matrix[3, 4, Int]] = ArbitraryMatrix[3, 4, Int]

      forAll { (mg: Matrix[3, 4, Int]) =>
         val md: Matrix[3, 4, Int] = MatrixFactory[3, 4, Int].fromTuple(
            (mg(0, 0), mg(0, 1), mg(0, 2), mg(0, 3)),
            (mg(1, 0), mg(1, 1), mg(1, 2), mg(1, 3)),
            (mg(2, 0), mg(2, 1), mg(2, 2), mg(2, 3))
         )

         md === mg shouldBe true
      }

   it should "not compile with wrong row dimension" in:
      assertDoesNotCompile("""
         MatrixFactory[4, 3, Int].fromTuple(
            (1, 2, 3),
            (4, 5, 6),
            (7, 8, 9)
         )""")

   it should "not compile with wrong column dimension" in:
      assertDoesNotCompile("""
         MatrixFactory[3, 3, Int].fromTuple(
            (1, 2),
            (3, 4),
            (5, 6)
         )""")

   it should "not compile with wrong column dimension in one row" in:
      assertDoesNotCompile("""
         MatrixFactory[3, 2, Int].fromTuple(
            (1, 2),
            (3, 4, 5),
            (6, 7)
         )""")

   it should "not compile with wrong element type" in:
      assertDoesNotCompile("""
         MatrixFactory[3, 2, Int].fromTuple(
            (1, 2),
            (3, 4.0),
            (5, 6)
         )""")
