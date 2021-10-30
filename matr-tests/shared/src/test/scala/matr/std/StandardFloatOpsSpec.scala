package matr.std:

   import matr.ElementMultiplication
   import matr.MatrFlatSpec
   import matr.std.StandardFloatOps.given

   class StandardFloatOpsSpec extends MatrFlatSpec:

      "floatMultiplication" should "multiply two Floats" in {
         val floatMul = summon[ElementMultiplication[Float, Float]]
         forAll { (a: Float, b: Float) =>
            floatMul.times(a, b) shouldEqual (a * b)
         }
      }

      "floatBigDecimalMultiplication" should "multiply a Float and a BigDecimal" in {
         val floatBigDecimalMul = summon[ElementMultiplication[Float, BigDecimal]]
         forAll { (a: Float, b: BigDecimal) =>
            floatBigDecimalMul.times(a, b) shouldEqual (a.toDouble * b)
         }
      }

      "floatDoubleMultiplication" should "multiply a Float and a Double" in {
         val floatDoubleMul = summon[ElementMultiplication[Float, Double]]
         forAll { (a: Float, b: Double) =>
            floatDoubleMul.times(a, b) shouldEqual (a * b)
         }
      }

      "floatIntMultiplication" should "multiply a Float and an Integer" in {
         val floatIntMul = summon[ElementMultiplication[Float, Int]]
         forAll { (a: Float, b: Int) =>
            floatIntMul.times(a, b) shouldEqual (a * b)
         }
      }

      "floatLongMultiplication" should "multiply an Float and a Long" in {
         val floatLongMul = summon[ElementMultiplication[Float, Long]]
         forAll { (a: Float, b: Long) =>
            floatLongMul.times(a, b) shouldEqual (a * b)
         }
      }
