package test.matr.impl

import matr.Matrix
import matr.dflt.DefaultMatrixFactory.given
import matr.dflt.DefaultMatrixOps.given
import matr.util.RowMajorIndex
import matr.std.StandardOps.given
import matr.MatrFlatSpec
import matr.MatrixFactory
import scala.util.Random
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import org.scalacheck.Gen.Choose

class MatrixFoldSpec extends MatrFlatSpec:

   "Folding over a Scalar" should
      "invoke the given function with the index (0, 0) and return the accumulated result" in {

         forAll(genElements(1)) { (elements: List[Int]) =>
            val m: Matrix[1, 1, Int] = MatrixFactory[1, 1, Int].rowMajor(elements*)

            val res: Int =
               m.fold(0) { (curr: Int, rowIdx: Int, colIdx: Int) =>
                  curr + m(rowIdx, colIdx)
               }

            val resExp = elements.sum
            res shouldEqual resExp
         }
      }

   "Folding over a 1x2 Matrix" should
      "invoke the given function for each element with the correct indices and return the accumulated result" in {

         forAll(genElements(2)) { (elements: List[Int]) =>
            val m: Matrix[1, 2, Int] = MatrixFactory[1, 2, Int].rowMajor(elements*)

            val res: Int =
               m.fold(0) { (curr: Int, rowIdx: Int, colIdx: Int) =>
                  curr + m(rowIdx, colIdx)
               }

            val resExp = elements.sum
            res shouldEqual resExp
         }
      }

   "Folding over a 2x1 Matrix" should
      "invoke the given function for each element with the correct indices and return the accumulated result" in {

         forAll(genElements(2)) { (elements: List[Int]) =>
            val m: Matrix[2, 1, Int] = MatrixFactory[2, 1, Int].rowMajor(elements*)

            val res: Int =
               m.fold(0) { (curr: Int, rowIdx: Int, colIdx: Int) =>
                  curr + m(rowIdx, colIdx)
               }

            val resExp = elements.sum
            res shouldEqual resExp
         }
      }

   "Folding over a 9x4 Matrix" should
      "invoke the given function for each element with the correct indices and return the accumulated result" in {

         forAll(genElements(36)) { (elements: List[Int]) =>
            val m: Matrix[9, 4, Int] = MatrixFactory[9, 4, Int].rowMajor(elements*)

            val res: Int =
               m.fold(0) { (curr: Int, rowIdx: Int, colIdx: Int) =>
                  curr + m(rowIdx, colIdx)
               }

            val resExp = elements.sum
            res shouldEqual resExp
         }
      }

   "Folding over a 8x8 Matrix" should
      "invoke the given function for each element with the correct indices and return the accumulated result" in {

         forAll(genElements(64)) { (elements: List[Int]) =>
            val m: Matrix[8, 8, Int] = MatrixFactory[8, 8, Int].rowMajor(elements*)

            val res: Int =
               m.fold(0) { (curr: Int, rowIdx: Int, colIdx: Int) =>
                  curr + m(rowIdx, colIdx)
               }

            val resExp = elements.sum
            res shouldEqual resExp
         }
      }

   private def genElements(size: Int): Gen[List[Int]] = Gen.containerOfN[List, Int](
      size,
      Gen.chooseNum(-1000, 1000)
   )
