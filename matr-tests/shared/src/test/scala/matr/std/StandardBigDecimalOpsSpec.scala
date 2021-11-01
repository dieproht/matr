package matr.std:

   import matr.ElementMultiplication
   import matr.MatrFlatSpec
   import matr.std.StandardBigDecimalOps.given

   class StandardBigDecimalOpsSpec extends MatrFlatSpec:

      "bigDecimalMultiplication" should "multiply two BigDecimals" in {
         val bigDecimalMul = summon[ElementMultiplication[BigDecimal, BigDecimal]]
         forAll { (a: BigDecimal, b: BigDecimal) =>
            bigDecimalMul.times(a, b) shouldEqual (a * b)
         }
      }

      "bigDecimalDoubleMultiplication" should "multiply a BigDecimal and a Double" in {
         val mul = summon[ElementMultiplication[BigDecimal, Double]]
         forAll { (a: BigDecimal, b: Double) =>
            mul.times(a, b) shouldEqual (a * b)
         }
      }

      "bigDecimalFloatMultiplication" should "multiply a BigDecimal and a Float" in {
         val mul = summon[ElementMultiplication[BigDecimal, Float]]
         forAll { (a: BigDecimal, b: Float) =>
            mul.times(a, b) shouldEqual (a * b.toDouble)
         }
      }

      "bigDecimalIntMultiplication" should "multiply a BigDecimal and an Int" in {
         val mul = summon[ElementMultiplication[BigDecimal, Int]]
         forAll { (a: BigDecimal, b: Int) =>
            mul.times(a, b) shouldEqual (a * b)
         }
      }

      "bigDecimalLongMultiplication" should "multiply a BigDecimal and a Long" in {
         val mul = summon[ElementMultiplication[BigDecimal, Long]]
         forAll { (a: BigDecimal, b: Long) =>
            mul.times(a, b) shouldEqual (a * b)
         }
      }
