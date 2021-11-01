package matr.dflt:

   import matr.ArbitraryMatrix
   import matr.Matrix
   import matr.MatrixSubtraction
   import matr.MatrixFactory
   import matr.MatrFlatSpec
   import matr.dflt.DefaultMatrixFactory.given
   import matr.dflt.DefaultMatrixOps.given
   import matr.std.StandardOps.given
   import org.scalacheck.Arbitrary

   class DefaultMatrixSubtractionSpec extends MatrFlatSpec:

      "Matrix subtraction" should "subtract two matrices" in {

         val matrixIntSub = summon[MatrixSubtraction[3, 2, Int]]

         given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

         forAll { (m1: Matrix[3, 2, Int], m2: Matrix[3, 2, Int]) =>
            val resExp = MatrixFactory[3, 2, Int].rowMajor(
               m1(0, 0) - m2(0, 0),
               m1(0, 1) - m2(0, 1),
               m1(1, 0) - m2(1, 0),
               m1(1, 1) - m2(1, 1),
               m1(2, 0) - m2(2, 0),
               m1(2, 1) - m2(2, 1)
            )

            val resSub = matrixIntSub.minus(m1, m2)

            resSub === resExp shouldBe true
         }
      }
