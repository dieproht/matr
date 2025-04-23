package matr.dflt

import matr.Inverse
import matr.Matrix
import math.Numeric.Implicits.infixNumericOps
import matr.MatrixFactory
import matr.ElementDivision
import matr.ElementMultiplication
import matr.MatrixNotInvertibleException

/** Default determinant implementation based on Gauss Jordan method.
  */
trait DefaultInverse:

   given defaultInverse[R <: Int, C <: Int, T]
            (using Matrix.Requirements.IsSquare[R, C])
            (using
             ElementMultiplication.Aux[T, T, T],
             ElementDivision.Aux[T, T, T],
             Numeric[T],
             MatrixFactory[R, C, T]
            )
            : Inverse[R, C, T] with

      def inv(m: M): M =
         val num = summon[Numeric[T]]
         val mul = summon[ElementMultiplication.Aux[T, T, T]]
         val div = summon[ElementDivision.Aux[T, T, T]]
         val mf = summon[MatrixFactory[R, C, T]]

         val n = m.rowDim
         val a = m.modify
         val b = mf.identity.modify

         for j <- 0 until n do
            var p = j
            while p < n && a(p, j) == num.zero do
               p = p + 1
            if p == n then
               throw new MatrixNotInvertibleException()
            if p != j then
               swapRows(a, j, p)
               swapRows(b, j, p)
            val f = a(j, j)
            for k <- 0 until n do
               a(j, k) = div.div(a(j, k), f)
               b(j, k) = div.div(b(j, k), f)
            for i <- 0 until n do
               if i != j then
                  val g = a(i, j)
                  for k <- 0 until n do
                     a(i, k) = a(i, k) - mul.times(g, a(j, k))
                     b(i, k) = b(i, k) - mul.times(g, b(j, k))

         b.result

      private def swapRows(mb: Matrix.Builder[R, C, T], r1Idx: Int, r2Idx: Int): Unit = 
        for idx <- 0 until mb.colDim do 
          val buf = mb(r1Idx, idx)
          mb(r1Idx, idx) = mb(r2Idx, idx)
          mb(r2Idx, idx) = buf

object DefaultInverse extends DefaultInverse
