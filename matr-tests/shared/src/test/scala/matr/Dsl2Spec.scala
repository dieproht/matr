package matr.dsl2

import matr.ArbitraryMatrix
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.Matrix
import matr.std.StandardOps.given
import org.scalacheck.Arbitrary
import matr.MatrixFactory
import matr.MatrFlatSpec
import matr.dsl2._

class Dsl2Spec extends MatrFlatSpec:

     "DSL-built Matrix" should "equal given Matrix 3x2" in {

          given Arbitrary[Matrix[3, 2, Int]] = ArbitraryMatrix[3, 2, Int]

          forAll { (mg: Matrix[3, 2, Int]) =>
               val md: Matrix[3, 2, Int] =
                    M[3, 2, Int]
                         || (mg(0, 0), mg(0, 1))
                         || (mg(1, 0), mg(1, 1))
                         || (mg(2, 0), mg(2, 1))
                         || $

               md === mg shouldBe true
          }
     }

     it should "equal given Matrix 3x4" in {

          given Arbitrary[Matrix[3, 4, Int]] = ArbitraryMatrix[3, 4, Int]

          forAll { (mg: Matrix[3, 4, Int]) =>
               val md: Matrix[3, 4, Int] =
                    M[3, 4, Int]
                         || (mg(0, 0), mg(0, 1), mg(0, 2), mg(0, 3))
                         || (mg(1, 0), mg(1, 1), mg(1, 2), mg(1, 3))
                         || (mg(2, 0), mg(2, 1), mg(2, 2), mg(2, 3))
                         || $

               md === mg shouldBe true
          }
     }
