package matr

import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import org.scalacheck.Arbitrary

class MatrixModifySpec extends MatrFlatSpec:

   "Not modifying the given Matrix" should "return the same Matrix" in:

      given Arbitrary[Matrix[4, 4, Double]] = ArbitraryMatrix[4, 4, Double]

      forAll { (m: Matrix[4, 4, Double]) =>
         val mRes: Matrix[4, 4, Double] = m.modify.result
         mRes === m shouldBe true
      }

   "Modifying one element" should "return the given Matrix modified at the expected place" in:

      given Arbitrary[Matrix[2, 2, Double]] = ArbitraryMatrix[2, 2, Double]

      forAll { (m: Matrix[2, 2, Double]) =>
         val e01new = m(0, 1) * 2

         val buildr = m.modify
         buildr(0, 1) = e01new
         val mRes: Matrix[2, 2, Double] = buildr.result

         val mExp: Matrix[2, 2, Double] = MatrixFactory[2, 2, Double].rowMajor(
            m(0, 0),
            e01new,
            m(1, 0),
            m(1, 1)
         )

         mRes === m shouldBe false
         mRes === mExp shouldBe true
      }
