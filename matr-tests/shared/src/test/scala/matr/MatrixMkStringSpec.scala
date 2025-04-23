package matr

import matr.dflt.DefaultMatrixFactory.given
import java.util.Locale

class MatrixMkStringSpec extends MatrFlatSpec:

   Locale.setDefault(Locale.ENGLISH)

   "Matrix.mkString" should "correctly render 3x2 Int Matrix" in:

      val m: Matrix[3, 2, Int] = MatrixFactory[3, 2, Int].rowMajor(3, 489, 2234, 1, 49, 4)

      val mStr: String = m.mkString

      val mStrExp = "⎛   3, 489⎞" + '\n' + "⎜2234,   1⎟" + '\n' + "⎝  49,   4⎠"

      mStrExp shouldEqual mStr

   it should "correctly render 2x2 Int Matrix" in:

      val m: Matrix[2, 2, Int] = MatrixFactory[2, 2, Int].rowMajor(3, 489, 49, 4)

      val mStr: String = m.mkString

      val mStrExp = "⎛ 3, 489⎞" + '\n' + "⎝49,   4⎠"

      mStrExp shouldEqual mStr

   it should "correctly render 2x1 Int Matrix" in:

      val m: Matrix[2, 1, Int] = MatrixFactory[2, 1, Int].rowMajor(3, 49)

      val mStr: String = m.mkString

      val mStrExp = "⎛ 3⎞" + '\n' + "⎝49⎠"

      mStrExp shouldEqual mStr

   it should "correctly render 4x1 Int Matrix" in:

      val m: Matrix[4, 1, Int] = MatrixFactory[4, 1, Int].rowMajor(3, 1789, 0, 49)

      val mStr: String = m.mkString

      val mStrExp = "⎛   3⎞" + '\n' + "⎜1789⎟" + '\n' + "⎜   0⎟" + '\n' + "⎝  49⎠"

      mStrExp shouldEqual mStr

   it should "correctly render 1x2 Int Matrix" in:

      val m: Matrix[1, 2, Int] = MatrixFactory[1, 2, Int].rowMajor(3, 489)

      val mStr: String = m.mkString

      val mStrExp = "(3, 489)"

      mStrExp shouldEqual mStr

   it should "correctly render 1x4 Int Matrix" in:

      val m: Matrix[1, 4, Int] = MatrixFactory[1, 4, Int].rowMajor(3, 489, 34, 9)

      val mStr: String = m.mkString

      val mStrExp = "(3, 489, 34, 9)"

      mStrExp shouldEqual mStr

   it should "correctly render 1x1 Int Matrix" in:

      val m: Matrix[1, 1, Int] = MatrixFactory[1, 1, Int].rowMajor(486)

      val mStr: String = m.mkString

      val mStrExp = "(486)"

      mStrExp shouldEqual mStr

   it should "correctly render 4x5 Double Matrix" in:

      val m: Matrix[4, 5, Double] = MatrixFactory[4, 5, Double].rowMajor(
         3.14, 489.9, 0.0, 78.34, 90.3, 2234.423, 1.0, 27.543, 5.6, 65.4, 49.0, 30.00009, 4.41,
         35.5, 6.4, 98765.8, 4.9, 5.2, 2.0, 2.1
      )

      val mStr: String = m.mkString

      val mStrExp =
         "⎛    3.14,    489.9,    0.0, 78.34, 90.3⎞" + '\n' +
            "⎜2234.423,      1.0, 27.543,   5.6, 65.4⎟" + '\n' +
            "⎜    49.0, 30.00009,   4.41,  35.5,  6.4⎟" + '\n' +
            "⎝ 98765.8,      4.9,    5.2,   2.0,  2.1⎠"

      mStrExp shouldEqual mStr

   "Matrix.mkString with custom element renderer" should "correctly render 2x3 Double Matrix" in:

      val m: Matrix[2, 3, Double] = MatrixFactory[2, 3, Double].rowMajor(
         3.14, 489.9, 0.0, 2234.423, 1.0, 27.543
      )

      def renderDouble(e: Double) = String.format("%.4f", e)

      val mStr: String = m.mkString(renderDouble)

      val mStrExp = "⎛   3.1400, 489.9000,  0.0000⎞" + '\n' + "⎝2234.4230,   1.0000, 27.5430⎠"

      mStrExp shouldEqual mStr
