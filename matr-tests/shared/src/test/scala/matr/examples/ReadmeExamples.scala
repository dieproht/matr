package matr.examples

object ReadmeExamples extends App:

   // Bring API interfaces in scope
   import matr.{Matrix, MatrixFactory}
   // Bring bundled implementations in scope
   import matr.MatrBundle.given

   // Create a Matrix from tuples
   val a: Matrix[3, 4, Int] = MatrixFactory[3, 4, Int].fromTuple(
      (0, 8, 15, 0),
      (4, 7, 1, 1),
      (1, 2, 3, 4)
   )

   // Create a Matrix of ones
   val b: Matrix[3, 4, Int] = MatrixFactory[3, 4, Int].ones

   // Add two matrices
   val c: Matrix[3, 4, Int] = a + b

   // Create a Matrix of random numbers
   val d: Matrix[4, 2, Int] = MatrixFactory[4, 2, Int].tabulate((_, _) =>
      scala.util.Random.nextInt(20)
   )

   // Calculate the dot product of two Matrices
   val e: Matrix[3, 2, Int] = c dot d

   // Transpose a Matrix
   val f: Matrix[2, 3, Int] = e.transpose

   // Pretty print a Matrix
   println(f.mkString)
// ... it outputs something like this:
// ⎛ 73,  77, 116⎞
// ⎝430, 253, 179⎠

/*
   // Element (2, 3) is missing when creating the Matrix
   val ae =
      MatrixFactory[3, 4, Int].fromTuple(
         (0, 8, 15, 0),
         (4, 7, 1, 1),
         (1, 2, 3)
      )

   // Adding two Matrices with different dimensions is not possible
   val be = MatrixFactory[2, 5, Int].ones
   val ce = a + be

   // Column dimension of left-hande side and row dimension of right-hand side
   // must be equal when calculating dot product
   val de = MatrixFactory[1, 2, Int].tabulate((_, _) => scala.util.Random.nextInt(20))
   val ee = c dot de
 */
