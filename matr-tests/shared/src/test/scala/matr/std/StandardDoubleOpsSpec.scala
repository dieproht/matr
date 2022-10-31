package matr.std

import matr.ElementDivision
import matr.ElementMultiplication
import matr.MatrFlatSpec
import matr.std.StandardDoubleOps.given

class StandardDoubleOpsSpec extends MatrFlatSpec:

   "doubleMultiplication" should "multiply two Doubles" in {
      val doubleMul = summon[ElementMultiplication[Double, Double]]
      forAll { (a: Double, b: Double) =>
         doubleMul.times(a, b) shouldEqual (a * b)
      }
   }

   "doubleBigDecimalMultiplication" should "multiply a Double and a BigDecimal" in {
      val doubleBigDecimalMul = summon[ElementMultiplication[Double, BigDecimal]]
      forAll { (a: Double, b: BigDecimal) =>
         doubleBigDecimalMul.times(a, b) shouldEqual (a * b)
      }
   }

   "doubleFloatMultiplication" should "multiply a Double and a Float" in {
      val doubleFloatMul = summon[ElementMultiplication[Double, Float]]
      forAll { (a: Double, b: Float) =>
         doubleFloatMul.times(a, b) shouldEqual (a * b)
      }
   }

   "doubleIntMultiplication" should "multiply a Double and an Integer" in {
      val doubleIntMul = summon[ElementMultiplication[Double, Int]]
      forAll { (a: Double, b: Int) =>
         doubleIntMul.times(a, b) shouldEqual (a * b)
      }
   }

   "doubleLongMultiplication" should "multiply a Double and a Long" in {
      val doubleLongMul = summon[ElementMultiplication[Double, Long]]
      forAll { (a: Double, b: Long) =>
         doubleLongMul.times(a, b) shouldEqual (a * b)
      }
   }

   "doubleDivision" should "divide two Doubles" in {
      val doubleDiv = summon[ElementDivision[Double, Double]]
      forAll { (a: Double, b: Double) =>
         doubleDiv.div(a, b) shouldEqual (a / b)
      }
   }
