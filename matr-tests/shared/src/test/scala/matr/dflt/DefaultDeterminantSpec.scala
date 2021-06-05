package matr.dflt:

  import matr.ArbitraryMatrix
  import matr.Matrix
  import matr.MatrixFactory
  import matr.Determinant
  import matr.MatrFlatSpec
  import matr.dflt.DefaultMatrixFactory.given
  import matr.dflt.DefaultMatrixOps.given
  import matr.std.StandardOps.given
  import org.scalacheck.Arbitrary


  class DefaultDeterminantSpec extends MatrFlatSpec:


    "DefaultDeterminant" should "return correct determinant for 1x1 Matrix" in {
      
      val determinant: Determinant[1, 1, Int] = summon[Determinant[1, 1, Int]]

      given Arbitrary[Matrix[1, 1, Int]] = ArbitraryMatrix[1, 1, Int]

      forAll { (m: Matrix[1, 1, Int]) => 
        val resExp: Int = m(0, 0)
        val res = determinant.det(m)
        res shouldEqual resExp
      }
    }


    it should "return correct determinant for 3x3 Matrix" in {
      
      val determinant: Determinant[3, 3, Int] = summon[Determinant[3, 3, Int]]

      given Arbitrary[Matrix[3, 3, Int]] = ArbitraryMatrix[3, 3, Int]

      forAll { (m: Matrix[3, 3, Int]) => 
        val (a, b, c) = (m(0, 0), m(0, 1), m(0, 2))
        val (d, e, f) = (m(1, 0), m(1, 1), m(1, 2))
        val (g, h, i) = (m(2, 0), m(2, 1), m(2, 2))
        val resExp: Int = a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g)

        val res = determinant.det(m)
           
        res shouldEqual resExp
      }
    }
