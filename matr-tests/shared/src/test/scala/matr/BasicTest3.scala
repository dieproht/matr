package matr

import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given

import TupleSupport.given

object BasicTest3:

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

      val m1p2 = m1 + m2
      println("m1p2= " + m1p2.mkString)

      val m100 = MatrixFactory[7, 2, Double].ones
      println("m100= " + m100.mkString)

      val m3 = MatrixFactory[3, 4, Int].fromTuple( //
         (0, 8, 30, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
      println("m3= " + m3.mkString)

      val m4 = MatrixFactory[3, 4, Int].ones
      println("m4= " + m4.mkString)

      val m200 = MatrixFactory[7, 2, Double].zeros
      println("m200= " + m200.mkString)

      val m5 = MatrixFactory[4, 3, Int].ones
      println("m5= " + m5.mkString)

      val m3m5 = m3 dot m5
      println("m3m5= " + m3m5.mkString)

      val m6 = MatrixFactory[3, 3, Int].zeros
      println("m6= " + m6.mkString)

      println("hihi")
