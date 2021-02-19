package matr.std:

  import matr.ElementAddition
  import matr.ElementSubtraction
  import matr.ElementMultiplication
  import matr.MatrFlatSpec
  import matr.std.StandardDoubleOps.given


  class StandardDoubleOpsSpec extends MatrFlatSpec:
  
  
    "doubleAddition" should "add two Doubles" in {
      val doubleAdd = summon[ElementAddition[Double]]
      forAll{ (a: Double, b: Double) =>
        doubleAdd.plus(a, b) shouldEqual (a + b)
      }
    }


    "doubleSubtraction" should "subtract two Doubles" in {
      val doubleSub = summon[ElementSubtraction[Double]]
      forAll{ (a: Double, b: Double) =>
        doubleSub.minus(a, b) shouldEqual (a - b)
      }
    }


    "doubleMultiplication" should "multiply two Doubles" in {
      val doubleMul = summon[ElementMultiplication[Double, Double]]
      forAll{ (a: Double, b: Double) =>
        doubleMul.times(a, b) shouldEqual (a * b)
      }
    }

    
    "doubleFloatMultiplication" should "multiply a Double and a Float" in {
      val doubleFloatMul = summon[ElementMultiplication[Double, Float]]
      forAll{ (a: Double, b: Float) =>
        doubleFloatMul.times(a, b) shouldEqual (a * b)
      }
    }

    
    "doubleIntMultiplication" should "multiply a Double and an Integer" in {
      val doubleIntMul = summon[ElementMultiplication[Double, Int]]
      forAll{ (a: Double, b: Int) =>
        doubleIntMul.times(a, b) shouldEqual (a * b)
      }
    }


    "doubleLongMultiplication" should "multiply a Double and a Long" in {
      val doubleLongMul = summon[ElementMultiplication[Double, Long]]
      forAll{ (a: Double, b: Long) =>
        doubleLongMul.times(a, b) shouldEqual (a * b)
      }
    }
