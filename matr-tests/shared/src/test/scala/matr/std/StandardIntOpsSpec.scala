package matr.std

import matr.ElementMultiplication
import matr.MatrFlatSpec
import matr.std.StandardIntOps.given

class StandardIntOpsSpec extends MatrFlatSpec:

   "intMultiplication" should "multiply two Integers" in {
      val intMul = summon[ElementMultiplication[Int, Int]]
      forAll { (a: Int, b: Int) =>
         intMul.times(a, b) shouldEqual (a * b)
      }
   }

   "intBigDecimalMultiplication" should "multiply an Integer and a BigDecimal" in {
      val intBigDecimalMul = summon[ElementMultiplication[Int, BigDecimal]]
      forAll { (a: Int, b: BigDecimal) =>
         intBigDecimalMul.times(a, b) shouldEqual (a * b)
      }
   }

   "intDoubleMultiplication" should "multiply an Integer and a Double" in {
      val intDoubleMul = summon[ElementMultiplication[Int, Double]]
      forAll { (a: Int, b: Double) =>
         intDoubleMul.times(a, b) shouldEqual (a * b)
      }
   }

   "intFloatMultiplication" should "multiply an Integer and a Float" in {
      val intFloatMul = summon[ElementMultiplication[Int, Float]]
      forAll { (a: Int, b: Float) =>
         intFloatMul.times(a, b) shouldEqual (a * b)
      }
   }

   "intLongMultiplication" should "multiply an Integer and a Long" in {
      val intLongMul = summon[ElementMultiplication[Int, Long]]
      forAll { (a: Int, b: Long) =>
         intLongMul.times(a, b) shouldEqual (a * b)
      }
   }
