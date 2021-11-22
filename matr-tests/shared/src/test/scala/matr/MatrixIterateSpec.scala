package matr

import matr.Matrix
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.util.RowMajorIndex
import matr.std.StandardOps.given

class MatrixIterateSpec extends MatrFlatSpec:

   "Iterating a Scalar" should "invoke the given function one time with the index (0, 0)" in {

      val m: Matrix[1, 1, Int] = MatrixFactory[1, 1, Int].zeros

      var invocationCount: Int = 0
      def fn(rowIdx: Int, colIdx: Int): Unit =
         (rowIdx, colIdx) shouldEqual RowMajorIndex.fromIdx(invocationCount, m.colDim)
         invocationCount = invocationCount + 1

      m.iterate(fn)

      invocationCount shouldEqual 1
   }

   "Iterating a 1x2 Matrix" should
      "invoke the given function two times with the correct indices" in {

         val m: Matrix[1, 2, Int] = MatrixFactory[1, 2, Int].zeros

         var invocationCount: Int = 0
         def fn(rowIdx: Int, colIdx: Int): Unit =
            (rowIdx, colIdx) shouldEqual RowMajorIndex.fromIdx(invocationCount, m.colDim)
            invocationCount = invocationCount + 1

         m.iterate(fn)

         invocationCount shouldEqual 2
      }

   "Iterating a 2x1 Matrix" should
      "invoke the given function two times with the correct indices" in {

         val m: Matrix[2, 1, Int] = MatrixFactory[2, 1, Int].zeros

         var invocationCount: Int = 0
         def fn(rowIdx: Int, colIdx: Int): Unit =
            (rowIdx, colIdx) shouldEqual RowMajorIndex.fromIdx(invocationCount, m.colDim)
            invocationCount = invocationCount + 1

         m.iterate(fn)

         invocationCount shouldEqual 2
      }

   "Iterating a 3x7 Matrix" should
      "invoke the given function 21 times with the correct indices" in {

         val m: Matrix[3, 7, Int] = MatrixFactory[3, 7, Int].zeros

         var invocationCount: Int = 0
         def fn(rowIdx: Int, colIdx: Int): Unit =
            (rowIdx, colIdx) shouldEqual RowMajorIndex.fromIdx(invocationCount, m.colDim)
            invocationCount = invocationCount + 1

         m.iterate(fn)

         invocationCount shouldEqual 21
      }

   "Iterating a 8x8 Matrix" should
      "invoke the given function 64 times with the correct indices" in {

         val m: Matrix[8, 8, Int] = MatrixFactory[8, 8, Int].zeros

         var invocationCount: Int = 0
         def fn(rowIdx: Int, colIdx: Int): Unit =
            (rowIdx, colIdx) shouldEqual RowMajorIndex.fromIdx(invocationCount, m.colDim)
            invocationCount = invocationCount + 1

         m.iterate(fn)

         invocationCount shouldEqual 64
      }
