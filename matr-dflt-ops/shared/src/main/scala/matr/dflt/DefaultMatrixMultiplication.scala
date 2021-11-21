package matr.dflt

import matr.ElementMultiplication
import matr.Matrix
import matr.MatrixFactory
import matr.MatrixMultiplication

trait DefaultMatrixMultiplication:

   given defaultMatrixMultiplication[R <: Int, C <: Int, L <: Int, T, U, X]
            (using
             em: ElementMultiplication.Aux[T, U, X],
             num: Numeric[X],
             mf: MatrixFactory[R, L, X]
            )
            (using vr: ValueOf[R], vc: ValueOf[C], vl: ValueOf[L])
            : MatrixMultiplication[R, C, L, T, U, X] with

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
                     buildr(k, l) = em.times(lhs(k, j), rhs(j, l))
                  else
                     buildr(k, l) = num.plus(buildr(k, l), em.times(lhs(k, j), rhs(j, l)))
                  l = l + 1
               k = k + 1
            j = j + 1
         buildr.result

object DefaultMatrixMultiplication extends DefaultMatrixMultiplication
