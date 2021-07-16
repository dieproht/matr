package matr:

  import matr.ArbitraryMatrix
  import matr.GenMatrix
  import matr.GenNumericMatrix
  import matr.Matrix
  import matr.dflt.DefaultMatrixFactory.given
  import matr.dflt.DefaultMatrixOps.given
  import matr.std.StandardOps.given
  import scala.util.Random
  import org.scalacheck.Arbitrary
  import org.scalacheck.Gen


  class MatrixSpec extends MatrFlatSpec:


    "Constructing a Matrix with invalid shape" should "not compile" in {
      assertDoesNotCompile("""val m: Matrix[0, 0, Float] = 
                                new Matrix[0, 0, Float]: 
                                  def apply(rowIdx: Int, colIdx: Int): Float = 1.23""")
    }
    

    "Both apply methods" should "give same element value 1x1" in {

      given Arbitrary[Matrix[1, 1, Int]] = ArbitraryMatrix[1, 1, Int]

      forAll { (m: Matrix[1, 1, Int]) => 
        m(0, 0) shouldEqual m[0, 0]
      }
    }


    it should "give same element value 2x2" in {

      given Arbitrary[Matrix[2, 2, Int]] = ArbitraryMatrix[2, 2, Int]

      forAll { (m: Matrix[2, 2, Int]) => 
        m(0, 0) shouldEqual m[0, 0]
        m(0, 1) shouldEqual m[0, 1]
        m(1, 0) shouldEqual m[1, 0]
        m(1, 1) shouldEqual m[1, 1]
      }
    }


    it should "give same element value 4x7" in {

      given Arbitrary[Matrix[4, 7, Int]] = ArbitraryMatrix[4, 7, Int]

      forAll { (m: Matrix[4, 7, Int]) => 
        m(0, 0) shouldEqual m[0, 0]
        m(3, 6) shouldEqual m[3, 6]
        m(3, 3) shouldEqual m[3, 3]
        m(2, 1) shouldEqual m[2, 1]
      }
    }

    
    "Matrix.rowDim" should "return row dimension" in {
      val m: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      m.rowDim shouldEqual 2
    }


    "Matrix.colDim" should "return column dimension" in {
      val m: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      m.colDim shouldEqual 3
    }


    "Comparing two matrices" should "require matrices with same element type" in {
      val m1: Matrix[6, 4, Double] = MatrixFactory[6, 4, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[6, 4, Int] = MatrixFactory[6, 4, Int].tabulate((_, _) => Random.nextInt)
      assertTypeError("val res = m1 === m2")
    }


    it should "require matrices with same shape" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[4, 4, Double] = MatrixFactory[4, 4, Double].tabulate((_, _) => Random.nextDouble)
      assertTypeError("val res = m1 === m2")
    }


    it should "give same result as the corresponding type class" in {
      val matrixEq = summon[MatrixEquality[7, 8, Int]]
      given Arbitrary[Matrix[7, 8, Int]] = ArbitraryMatrix[7, 8, Int]
      forAll { (m1: Matrix[7, 8, Int], m2: Matrix[7, 8, Int]) => 
        val tcResult: Boolean = matrixEq.matricesEqual(m1, m2)
        val mResult: Boolean = m1 === m2
        mResult shouldEqual tcResult
      }
    }
    
    
    "Adding two matrices" should "require matrices with same element type" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[2, 3, Float] = MatrixFactory[2, 3, Float].tabulate((_, _) => Random.nextFloat)
      assertTypeError("val sum = m1 + m2")
    }


    it should "require matrices with same shape" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[2, 7, Double] = MatrixFactory[2, 7, Double].tabulate((_, _) => Random.nextDouble)
      assertTypeError("val sum = m1 + m2")
    }


    it should "give same result as the corresponding type class" in {
      val matrixAdd = summon[MatrixAddition[3, 4, Int]]
      given Arbitrary[Matrix[3, 4, Int]] = ArbitraryMatrix[3, 4, Int]
      forAll { (m1: Matrix[3, 4, Int], m2: Matrix[3, 4, Int]) => 
        val tcResult: Matrix[3, 4, Int] = matrixAdd.plus(m1, m2)
        val mResult: Matrix[3, 4, Int] = m1 + m2
        mResult === tcResult shouldBe true
      }
    }


    "Subtracting two matrices" should "require matrices with same element type" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[2, 3, Float] = MatrixFactory[2, 3, Float].tabulate((_, _) => Random.nextFloat)
      assertTypeError("val sum = m1 - m2")
    }


    it should "require matrices with same shape" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[2, 7, Double] = MatrixFactory[2, 7, Double].tabulate((_, _) => Random.nextDouble)
      assertTypeError("val sum = m1 - m2")
    }


    it should "give same result as the corresponding type class" in {
      val matrixSub = summon[MatrixSubtraction[3, 4, Int]]
      given Arbitrary[Matrix[3, 4, Int]] = ArbitraryMatrix[3, 4, Int]
      forAll { (m1: Matrix[3, 4, Int], m2: Matrix[3, 4, Int]) => 
        val tcResult: Matrix[3, 4, Int] = matrixSub.minus(m1, m2)
        val mResult: Matrix[3, 4, Int] = m1 - m2
        mResult === tcResult shouldBe true
      }
    }


    "Multiplying two matrices" should "require that the row dim of the first equals the column dim of the second factor" in {
      val m1: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      val m2: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      assertTypeError("val prod = m1 dot m2")
    }


    it should "give same result as the corresponding type class" in {
      
      val matrixMul = summon[MatrixMultiplication[3, 4, 5, Int, Int]]

      val maxVal = 100

      val gen = for 
        g1 <- GenNumericMatrix[3, 4, Int](-1 * maxVal, maxVal)
        g2 <- GenNumericMatrix[4, 5, Int](-1 * maxVal, maxVal)
      yield
        (g1, g2)

      forAll(gen) { (m1: Matrix[3, 4, Int], m2: Matrix[4, 5, Int]) => 
        val tcResult: Matrix[3, 5, Int] = matrixMul.dot(m1, m2)
        val mResult: Matrix[3, 5, Int] = m1 dot m2
        mResult === tcResult shouldBe true
      }
    }


    "Transposing a matrix" should "give same result as the corresponding type class" in {
      val transp = summon[Transpose[2, 3, Int]]
      given Arbitrary[Matrix[2, 3, Int]] = ArbitraryMatrix[2, 3, Int]
      forAll { (m: Matrix[2, 3, Int]) => 
        val tcResult: Matrix[3, 2, Int] = transp.transpose(m)
        val mResult: Matrix[3, 2, Int] = m.transpose
        mResult === tcResult shouldBe true
      }
    }

    "Getting a submatrix" should "require that the top left coordinate is within matrix shape" in {
      val m: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      assertTypeError("val sub = m.sub[-1, 0, 1, 1]")
    }


    it should "require that the bottom right coordinate is within matrix shape" in {
      val m: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].tabulate((_, _) => Random.nextDouble)
      assertTypeError("val sub = m.sub[0, 0, 2, 3]")
    }


    it should "give same result as the corresponding type class (1)" in {
      val subm = summon[Submatrix[0, 0, 2, 3, 3, 4, Int]]
      given Arbitrary[Matrix[3, 4, Int]] = ArbitraryMatrix[3, 4, Int]
      forAll { (m: Matrix[3, 4, Int]) => 
        val tcResult: Matrix[3, 4, Int] = subm.submatrix(m)
        val mResult: Matrix[3, 4, Int] = m.submatrix[0, 0, 2, 3]
        mResult === tcResult shouldBe true
      }
    }


    it should "give same result as the corresponding type class (2)" in {
      val subm = summon[Submatrix[1, 1, 2, 2, 5, 4, Int]]
      given Arbitrary[Matrix[5, 4, Int]] = ArbitraryMatrix[5, 4, Int]
      forAll { (m: Matrix[5, 4, Int]) => 
        val tcResult: Matrix[2, 2, Int] = subm.submatrix(m)
        val mResult: Matrix[2, 2, Int] = m.submatrix[1, 1, 2, 2]
        mResult === tcResult shouldBe true
      }
    }

    "Calculating the determinant" should "require that this Matrix is squared (1)" in {
      assertCompiles("""val m: Matrix[5, 5, Int] = ???
                        val d: Int = m.det""")
    }
    
    it should "require that this Matrix is squared (2)" in {
      assertDoesNotCompile("""val m: Matrix[4, 5, Int] = ???
                              val d: Int = m.det""")
    }

    it should "give same result as the corresponding type class" in {
      val detTC = summon[Determinant[4, 4, Int]]
      given Arbitrary[Matrix[4, 4, Int]] = ArbitraryMatrix[4, 4, Int]
      forAll { (m: Matrix[4, 4, Int]) => 
        val tcResult: Int = detTC.det(m)
        val mResult: Int = m.det
        mResult shouldEqual tcResult
      }
    }
