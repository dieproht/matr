package matr.dflt:

  import matr.ElementAddition
  import matr.Matrix
  import matr.MatrixFactory
  import matr.MatrixMultiplication
  import matr.ElementMultiplication  

  trait DefaultMatrixMultiplication:
    
    given defaultMatrixMultiplication[R <: Int, C <: Int, L <: Int, T, U, X]
                                     (using vr: ValueOf[R], vc: ValueOf[C], vl: ValueOf[L])
                                     (using m: ElementMultiplication.Aux[T, U, X])
                                     (using a: ElementAddition[X])
                                     (using mf: MatrixFactory[R, L, X]) 
                                     : MatrixMultiplication[R, C, L, T, U] with
      
      def dot(lhs: Lhs, rhs: Rhs): Out = 
        val resultNumCols = vl.value
        val buildr = mf.builder
        var j: Int = 0 // lhs col
        while j < lhs.colDim do 
          var k: Int = 0 // result row
          while k < lhs.rowDim do
            var l: Int = 0 // result col
            while l < resultNumCols do
              if j == 0 then
                buildr(k, l) = m.times(lhs(k, j), rhs(j, l))
              else
                buildr(k, l) = a.plus(buildr(k, l), m.times(lhs(k, j), rhs(j, l)))
              l = l + 1
            k = k + 1
          j = j + 1      
        buildr.result

      type Out = Matrix[R, L, X]
 

  object DefaultMatrixMultiplication extends DefaultMatrixMultiplication