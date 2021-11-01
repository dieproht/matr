package matr

import matr.ArbitraryMatrix
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.dsl._
import matr.Matrix
import matr.std.StandardOps.given
import org.scalacheck.Arbitrary

class DslSpec extends MatrFlatSpec:

   "DSL-built Matrix" should "equal given Matrix 1x1" in {

      given Arbitrary[Matrix[1, 1, Int]] = ArbitraryMatrix[1, 1, Int]

      forAll { (mg: Matrix[1, 1, Int]) =>
         val md: Matrix[1, 1, Int] = ||[1, 1, Int]
            || mg(0, 0) || $$

         md === mg shouldBe true
      }
   }

   it should "equal given Matrix 1x2" in {

      given Arbitrary[Matrix[1, 2, Int]] = ArbitraryMatrix[1, 2, Int]

      forAll { (mg: Matrix[1, 2, Int]) =>
         val md: Matrix[1, 2, Int] = ||[1, 2, Int]
            || mg(0, 0) | mg(0, 1) || $$

         md === mg shouldBe true
      }
   }

   it should "equal given Matrix 2x1" in {

      given Arbitrary[Matrix[2, 1, Int]] = ArbitraryMatrix[2, 1, Int]

      forAll { (mg: Matrix[2, 1, Int]) =>
         val md: Matrix[2, 1, Int] = ||[2, 1, Int]
            || mg(0, 0) || $
            || mg(1, 0) || $$

         md === mg shouldBe true
      }
   }

   it should "equal given Matrix 2x2" in {

      given Arbitrary[Matrix[2, 2, Int]] = ArbitraryMatrix[2, 2, Int]

      forAll { (mg: Matrix[2, 2, Int]) =>
         val md: Matrix[2, 2, Int] = ||[2, 2, Int]
            || mg(0, 0) | mg(0, 1) || $
            || mg(1, 0) | mg(1, 1) || $$

         md === mg shouldBe true
      }
   }

   it should "equal given Matrix 4x7" in {

      given Arbitrary[Matrix[4, 7, Int]] = ArbitraryMatrix[4, 7, Int]

      forAll { (mg: Matrix[4, 7, Int]) =>
         val md: Matrix[4, 7, Int] = ||[4, 7, Int]
            || mg(0, 0) | mg(0, 1) | mg(0, 2) | mg(0, 3) | mg(0, 4) | mg(0, 5) | mg(0, 6) || $
            || mg(1, 0) | mg(1, 1) | mg(1, 2) | mg(1, 3) | mg(1, 4) | mg(1, 5) | mg(1, 6) || $
            || mg(2, 0) | mg(2, 1) | mg(2, 2) | mg(2, 3) | mg(2, 4) | mg(2, 5) | mg(2, 6) || $
            || mg(3, 0) | mg(3, 1) | mg(3, 2) | mg(3, 3) | mg(3, 4) | mg(3, 5) | mg(3, 6) || $$

         md === mg shouldBe true
      }
   }

   "Constructing Matrix with DSL" should "not compile when number of columns does not match" in {
      assertDoesNotCompile("""val m: Matrix[1, 3, Int] = || [1, 3, Int]
                                                         || 45 | 67 | 89 | 37 || $$""")
   }

   it should "not compile when number of columns dows not match in first row" in {
      assertDoesNotCompile("""val m: Matrix[2, 3, Int] = || [2, 3, Int]
                                                         || 85 | 21 || $
                                                         || 55 | 35 | 68 || $$""")
   }

   it should "not compile when number of columns dows not match in last row" in {
      assertDoesNotCompile("""val m: Matrix[2, 3, Int] = || [2, 3, Int]
                                                         || 85 | 21 | 68 || $ 
                                                         || 55 | 35 || $$""")
   }

   it should "not compile when number of columns dows not match in middle row" in {
      assertDoesNotCompile("""val m: Matrix[3, 3, Int] = || [3, 3, Int]
                                                         || 85 | 21 | 68 || $ 
                                                         || 85 | 13 | 99 | 83 || $
                                                         || 55 | 35 | 12 || $$""")
   }

   it should "not compile when number of rows does not match" in {
      assertDoesNotCompile("""val m: Matrix[3, 1, Int] = || [3, 1, Int]
                                                         || 45 || $
                                                         || 67 || $
                                                         || 89 || $
                                                         || 37 || $$""")
   }

   it should "not compile when number of rows dows not match in first column" in {
      assertDoesNotCompile("""val m: Matrix[3, 2, Int] = || [3, 2, Int]
                                                         || 85 | 21 || $
                                                         || 55 | 35 || $
                                                         || 68 | 98 || $
                                                         || 11 || $$""")
   }

   it should "not compile when number of rows dows not match in last column" in {
      assertDoesNotCompile("""val m: Matrix[3, 2, Int] = || [3, 2, Int]
                                                         || 85 | 21 || $
                                                         || 68 | 23 || $
                                                         || 55 || $$""")
   }

   it should "not compile when shape does not match" in {
      assertDoesNotCompile("""val m: Matrix[2, 2, Int] = || [2, 2, Int]
                                                         || 85 | 21 | 68 || $
                                                         || 85 | 13 | 99 || $
                                                         || 55 | 35 | 12 || $$""")
   }
