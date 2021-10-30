package matr.dflt:

   import matr.ArbitraryMatrix
   import matr.Matrix
   import matr.MatrixFactory
   import matr.Submatrix
   import matr.MatrFlatSpec
   import matr.dflt.DefaultMatrixFactory.given
   import matr.dflt.DefaultMatrixOps.given
   import matr.std.StandardOps.given
   import org.scalacheck.Arbitrary

   class DefaultSubmatrixSpec extends MatrFlatSpec:

      "DefaultSubmatrix" should "return correct Submatrix when (2,1) is top-left and (3, 3) bottom-right of Matrix 8x8" in {

         val submatrix: Submatrix[2, 1, 3, 3, 8, 8, Double] =
            summon[Submatrix[2, 1, 3, 3, 8, 8, Double]]

         given Arbitrary[Matrix[8, 8, Double]] = ArbitraryMatrix[8, 8, Double]

         forAll { (m: Matrix[8, 8, Double]) =>
            val resExp: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].rowMajor(
               m(2, 1),
               m(2, 2),
               m(2, 3),
               m(3, 1),
               m(3, 2),
               m(3, 3)
            )

            val resSubmatrix = submatrix.submatrix(m)

            resSubmatrix === resExp shouldBe true
         }
      }

      it should "return original Matrix when (0,0) is top-left and (7, 7) bottom-right of Matrix 8x8" in {

         val submatrix: Submatrix[0, 0, 7, 7, 8, 8, Double] =
            summon[Submatrix[0, 0, 7, 7, 8, 8, Double]]

         given Arbitrary[Matrix[8, 8, Double]] = ArbitraryMatrix[8, 8, Double]

         forAll { (m: Matrix[8, 8, Double]) =>
            val resSubmatrix = submatrix.submatrix(m)

            resSubmatrix === m shouldBe true
         }
      }

      it should "return correct Submatrix when (0,2) is top-left and (0, 4) bottom-right of Matrix 8x8" in {

         val submatrix: Submatrix[0, 2, 0, 4, 8, 8, Double] =
            summon[Submatrix[0, 2, 0, 4, 8, 8, Double]]

         given Arbitrary[Matrix[8, 8, Double]] = ArbitraryMatrix[8, 8, Double]

         forAll { (m: Matrix[8, 8, Double]) =>
            val resExp: Matrix[1, 3, Double] =
               MatrixFactory[1, 3, Double].rowMajor(m(0, 2), m(0, 3), m(0, 4))

            val resSubmatrix = submatrix.submatrix(m)

            resSubmatrix === resExp shouldBe true
         }
      }

      it should "return first column when (0,0) is top-left and (7, 0) bottom-right of Matrix 8x8" in {

         val submatrix: Submatrix[0, 0, 7, 0, 8, 8, Double] =
            summon[Submatrix[0, 0, 7, 0, 8, 8, Double]]

         given Arbitrary[Matrix[8, 8, Double]] = ArbitraryMatrix[8, 8, Double]

         forAll { (m: Matrix[8, 8, Double]) =>
            val resExp: Matrix[8, 1, Double] = MatrixFactory[8, 1, Double].rowMajor(
               m(0, 0),
               m(1, 0),
               m(2, 0),
               m(3, 0),
               m(4, 0),
               m(5, 0),
               m(6, 0),
               m(7, 0)
            )

            val resSubmatrix = submatrix.submatrix(m)

            resSubmatrix === resExp shouldBe true
         }
      }

      it should "return second column when (0,1) is top-left and (7, 1) bottom-right of Matrix 8x8" in {

         val submatrix: Submatrix[0, 1, 7, 1, 8, 8, Double] =
            summon[Submatrix[0, 1, 7, 1, 8, 8, Double]]

         given Arbitrary[Matrix[8, 8, Double]] = ArbitraryMatrix[8, 8, Double]

         forAll { (m: Matrix[8, 8, Double]) =>
            val resExp: Matrix[8, 1, Double] = MatrixFactory[8, 1, Double].rowMajor(
               m(0, 1),
               m(1, 1),
               m(2, 1),
               m(3, 1),
               m(4, 1),
               m(5, 1),
               m(6, 1),
               m(7, 1)
            )

            val resSubmatrix = submatrix.submatrix(m)

            resSubmatrix === resExp shouldBe true
         }
      }
