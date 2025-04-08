package matr

import matr.MatrBundle.given
import matr.Matrix
import matr.MatrixFactory
// import matr.dflt.DefaultMatrixBuilder.newDefaultMatrixBuilder

object BasicTest:
   @main
   def run(): Unit =
      println("start")
      val m1 = MatrixFactory[3, 4, Int].fromTuple( //
         (0, 8, 15, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
      println("m1= " + m1.mkString)

      val m2 = MatrixFactory[3, 4, Int].ones
      println("m2= " + m2.mkString)

      val m3 = MatrixFactory[3, 4, Int].fromTuple( //
         (0, 8, 30, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
      println("m3= " + m3.mkString)

      val m4 = MatrixFactory[3, 4, Int].ones
      println("m4= " + m4.mkString)

      println("hihi")
