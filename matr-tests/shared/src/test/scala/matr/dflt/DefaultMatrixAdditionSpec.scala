package matr.dflt:

  import matr.ArbitraryMatrix
  import matr.Matrix
  import matr.MatrixAddition
  import matr.MatrixFactory
  import matr.MatrFlatSpec
  import matr.dflt.DefaultMatrixFactory.given
  import matr.dflt.DefaultMatrixOps.given
  import matr.std.StandardOps.given
  import org.scalacheck.Arbitrary


  class DefaultMatrixAdditionSpec extends MatrFlatSpec:


    "Matrix addition" should "add two matrices" in {

      val matrixIntAdd = summon[MatrixAddition[3, 2, Int]]

      given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

      forAll { (m1: Matrix[3, 2, Int], m2: Matrix[3, 2, Int]) => 
        val resExp = MatrixFactory[3, 2, Int].rowMajor(m1(0, 0) + m2(0, 0), m1(0, 1) + m2(0, 1), 
                                                       m1(1, 0) + m2(1, 0), m1(1, 1) + m2(1, 1),
                                                       m1(2, 0) + m2(2, 0), m1(2, 1) + m2(2, 1))

        val resAdd = matrixIntAdd.plus(m1, m2)
           
        resAdd === resExp shouldBe true
      }
    }
