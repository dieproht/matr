package matr:
  
  object ReadmeExamples extends App: 
  
    import matr.dsl._
    import matr.Matrix
    import matr.MatrixFactory
    import matr.MatrBundle.given

    // Create a Matrix by DSL
    val a: Matrix[3, 4, Int] = 
      || [3, 4, Int]
      || 0 | 8 | 15 | 0 || $
      || 4 | 7 | 1  | 1 || $
      || 1 | 2 | 3  | 4 || $$

    // Create a Matrix of ones
    val b: Matrix[3, 4, Int] = MatrixFactory[3, 4, Int].ones
    
    // Add two matrices
    val c: Matrix[3, 4, Int] = a + b
    
    // Create a Matrix of random numbers
    val d: Matrix[4, 2, Int] = 
      MatrixFactory[4, 2, Int].tabulate((_, _) => scala.util.Random.nextInt(20))
    
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
      || [3, 4, Int]
      || 0 | 8 | 15 | 0 || $
      || 4 | 7 | 1  | 1 || $
      || 1 | 2 | 3  || $$

    
    // Adding two Matrices with different dimensions is not possible
    val be = MatrixFactory[2, 5, Int].ones
    val ce = a + be
   
    // Column dimension of left-hande side and row dimension of right-hand side 
    // must be equal when calculating dot product
    val de = MatrixFactory[1, 2, Int].tabulate((_, _) => scala.util.Random.nextInt(20))
    val ee = c dot de
*/ 
