package test.matr.impl

import matr.MatrFlatSpec
import matr.util.RowMajorIndex

class RowMajorIndexSpec extends MatrFlatSpec:

   "RowMajorIndex.toIdx" should "return zero for a Scalar" in {
      RowMajorIndex.toIdx(0, 0, 1) shouldEqual 0
   }

   it should "return correct index for every position in 1x2 Matrix" in {
      testToIdx(1, 2)
   }

   it should "return correct index for every position in 2x1 Matrix" in {
      testToIdx(2, 1)
   }

   it should "return correct index for every position in 3x4 Matrix" in {
      testToIdx(3, 4)
   }

   it should "return correct index for every position in 6x6 Matrix" in {
      testToIdx(6, 6)
   }

   it should "return correct index for every position in 12x18 Matrix" in {
      testToIdx(12, 18)
   }

   "RowMajorIndex.fromIdx" should "return (zero, zero) for a Scalar" in {
      RowMajorIndex.fromIdx(0, 1) shouldEqual (0, 0)
   }

   it should "return correct row index and colummn index for every position in 1x2 Matrix" in {
      testFromIdx(1, 2)
   }

   it should "return correct row index and colummn index for every position in 2x1 Matrix" in {
      testFromIdx(2, 1)
   }

   it should "return correct row index and colummn index for every position in 3x4 Matrix" in {
      testFromIdx(3, 4)
   }

   it should "return correct row index and colummn index for every position in 6x6 Matrix" in {
      testFromIdx(6, 6)
   }

   it should "return correct row index and colummn index for every position in 12x18 Matrix" in {
      testFromIdx(12, 18)
   }

   private def testToIdx(rowDim: Int, colDim: Int): Unit =
      var curIdx = 0
      for
         rowIdx <- 0 to rowDim - 1
         colIdx <- 0 to colDim - 1
      do
         RowMajorIndex.toIdx(rowIdx, colIdx, colDim) shouldEqual curIdx
         curIdx = curIdx + 1

   private def testFromIdx(rowDim: Int, colDim: Int): Unit =
      var curIdx = 0
      for
         rowIdx <- 0 to rowDim - 1
         colIdx <- 0 to colDim - 1
      do
         RowMajorIndex.fromIdx(curIdx, colDim) shouldEqual (rowIdx, colIdx)
         curIdx = curIdx + 1
