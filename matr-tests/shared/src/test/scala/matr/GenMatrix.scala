package matr:

  import org.scalacheck.Arbitrary
  import org.scalacheck.Gen
  import org.scalacheck.Gen.Choose

  object GenMatrix:

    def apply[R <: Int, C <: Int, T]
             (using Matrix.DimsOK[R, C] =:= true)
             (using vr: ValueOf[R], vc: ValueOf[C])
             (using mf: MatrixFactory[R, C, T])
             (using arb: Arbitrary[T])
             : Gen[Matrix[R, C, T]] = 
      Gen.containerOfN[List, T](vr.value * vc.value, arb.arbitrary)
        .map{ elements => 
          mf.rowMajor(elements*)
        }


  object GenNumericMatrix: 
  
    def apply[R <: Int, C <: Int, T](min: T, max: T)
             (using Matrix.DimsOK[R, C] =:= true)
             (using vr: ValueOf[R], vc: ValueOf[C])
             (using mf: MatrixFactory[R, C, T])
             (using Choose[T])
             (using Numeric[T])
             : Gen[Matrix[R, C, T]] = 
      Gen.containerOfN[List, T](vr.value * vc.value, Gen.chooseNum(min, max))
        .map{ elements => 
          mf.rowMajor(elements*)
        }
