package matr.std:

  import matr.ElementAddition
  import matr.ElementSubtraction
  import matr.ElementMultiplication
  import matr.MatrFlatSpec
  import matr.std.StandardFloatOps.given


  class StandardFloatOpsSpec extends MatrFlatSpec:
  
  
    "floatAddition" should "add two Floats" in {
      val floatAdd = summon[ElementAddition[Float]]
      forAll{ (a: Float, b: Float) =>
        floatAdd.plus(a, b) shouldEqual (a + b)
      }
    }


    "floatSubtraction" should "subtract two Floats" in {
      val floatSub = summon[ElementSubtraction[Float]]
      forAll{ (a: Float, b: Float) =>
        floatSub.minus(a, b) shouldEqual (a - b)
      }
    }


    "floatMultiplication" should "multiply two Floats" in {
      val floatMul = summon[ElementMultiplication[Float, Float]]
      forAll{ (a: Float, b: Float) =>
        floatMul.times(a, b) shouldEqual (a * b)
      }
    }

    
    "floatDoubleMultiplication" should "multiply a Float and a Double" in {
      val floatDoubleMul = summon[ElementMultiplication[Float, Double]]
      forAll{ (a: Float, b: Double) =>
        floatDoubleMul.times(a, b) shouldEqual (a * b)
      }
    }

    
    "floatIntMultiplication" should "multiply a Float and an Integer" in {
      val floatIntMul = summon[ElementMultiplication[Float, Int]]
      forAll{ (a: Float, b: Int) =>
        floatIntMul.times(a, b) shouldEqual (a * b)
      }
    }


    "floatLongMultiplication" should "multiply an Float and a Long" in {
      val floatLongMul = summon[ElementMultiplication[Float, Long]]
      forAll{ (a: Float, b: Long) =>
        floatLongMul.times(a, b) shouldEqual (a * b)
      }
    }
