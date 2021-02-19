package matr.std:

  import matr.ElementAddition
  import matr.ElementSubtraction
  import matr.ElementMultiplication
  import matr.MatrFlatSpec
  import matr.std.StandardLongOps.given


  class StandardLongOpsSpec extends MatrFlatSpec:
  
  
    "longAddition" should "add two Longs" in {
      val longAdd = summon[ElementAddition[Long]]
      forAll{ (a: Long, b: Long) =>
        longAdd.plus(a, b) shouldEqual (a + b)
      }
    }


    "longSubtraction" should "subtract two Longs" in {
      val longSub = summon[ElementSubtraction[Long]]
      forAll{ (a: Long, b: Long) =>
        longSub.minus(a, b) shouldEqual (a - b)
      }
    }


    "longMultiplication" should "multiply two Longs" in {
      val longMul = summon[ElementMultiplication[Long, Long]]
      forAll{ (a: Long, b: Long) =>
        longMul.times(a, b) shouldEqual (a * b)
      }
    }


    "longDoubleMultiplication" should "multiply a Long and a Double" in {
      val longDoubleMul = summon[ElementMultiplication[Long, Double]]
      forAll{ (a: Long, b: Double) =>
        longDoubleMul.times(a, b) shouldEqual (a * b)
      }
    }

    
    "longFloatMultiplication" should "multiply a Long and a Float" in {
      val longFloatMul = summon[ElementMultiplication[Long, Float]]
      forAll{ (a: Long, b: Float) =>
        longFloatMul.times(a, b) shouldEqual (a * b)
      }
    }

    
    "longIntMultiplication" should "multiply a Long and an Integer" in {
      val longIntMul = summon[ElementMultiplication[Long, Int]]
      forAll{ (a: Long, b: Int) =>
        longIntMul.times(a, b) shouldEqual (a * b)
      }
    }
