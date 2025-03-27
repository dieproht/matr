package matr

import matr.{Matrix, MatrixFactory2}
import matr.MatrBundle.given
// import matr.dflt.DefaultMatrixBuilder.newDefaultMatrixBuilder

object BasicTest:
   @main
   def run(): Unit =
      println("start")
      val mf = MatrixFactory2[3, 4, Int]
      val m = mf.fromTuple( //
         (0, 8, 15, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
      println(m.mkString)
      val m2 = mf.ones
      println(m2.mkString)
      println("hihi")
