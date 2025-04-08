package matr

// import matr.MatrBundle.given
import matr.Matrix
import matr.MatrixFactory3
import matr.dflt.DefaultMatrixOps.given
import matr.std.StandardOps.given
import TupleSupport.given
import matr.dflt.DefaultMatrixFactory.createDefaultMatrixBuilder
// import matr.dflt.DefaultMatrixBuilder.newDefaultMatrixBuilder

object BasicTest3:

   @main
   def run(): Unit =
      println("start")
      // val mf =
      val m1 = MatrixFactory3[3, 4, Int].fromTuple( //
         (0, 8, 15, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
      println("m1= " + m1.mkString)

      val m2 = MatrixFactory3[3, 4, Int].ones
      println("m2= " + m2.mkString)

      val m100 = MatrixFactory3[7, 2, Double].ones
      println("m100= " + m100.mkString)

      val m3 = MatrixFactory3[3, 4, Int].fromTuple( //
         (0, 8, 30, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
      println("m3= " + m3.mkString)

      val m4 = MatrixFactory3[3, 4, Int].ones
      println("m4= " + m4.mkString)

      val m200 = MatrixFactory3[7, 2, Double].zeros
      println("m200= " + m200.mkString)

      println("hihi")
