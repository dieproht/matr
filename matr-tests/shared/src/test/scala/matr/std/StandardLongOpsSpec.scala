package matr.std

import matr.ElementMultiplication
import matr.MatrFlatSpec
import matr.std.StandardLongOps.given

class StandardLongOpsSpec extends MatrFlatSpec:

   "longMultiplication" should "multiply two Longs" in:
      val longMul = summon[ElementMultiplication[Long, Long]]
      forAll { (a: Long, b: Long) =>
         longMul.times(a, b) shouldEqual (a * b)
      }

   "longBigDecimalMultiplication" should "multiply a Long and a BigDecimal" in:
      val longBigDecimalMul = summon[ElementMultiplication[Long, BigDecimal]]
      forAll { (a: Long, b: BigDecimal) =>
         longBigDecimalMul.times(a, b) shouldEqual (a * b)
      }

   "longDoubleMultiplication" should "multiply a Long and a Double" in:
      val longDoubleMul = summon[ElementMultiplication[Long, Double]]
      forAll { (a: Long, b: Double) =>
         longDoubleMul.times(a, b) shouldEqual (a * b)
      }

   "longFloatMultiplication" should "multiply a Long and a Float" in:
      val longFloatMul = summon[ElementMultiplication[Long, Float]]
      forAll { (a: Long, b: Float) =>
         longFloatMul.times(a, b) shouldEqual (a * b)
      }

   "longIntMultiplication" should "multiply a Long and an Integer" in:
      val longIntMul = summon[ElementMultiplication[Long, Int]]
      forAll { (a: Long, b: Int) =>
         longIntMul.times(a, b) shouldEqual (a * b)
      }
