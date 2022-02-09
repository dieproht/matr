package matr.dflt

import matr.Determinant
import matr.Inverse
import matr.Matrix
import math.Numeric.Implicits.infixNumericOps
import matr.MatrixFactory

trait DefaultInverse:

   given defaultInverse[R <: Int, C <: Int, T]
            (using Matrix.Requirements.IsSquare[R, C])
            (using Numeric[T])
            (using MatrixFactory[R, C, T])
            : Inverse[R, C, T] with

      def inv(m: M): M =
         val num = summon[Numeric[T]]
         val mf = summon[MatrixFactory[R, C, T]]

         val id = mf.identity

         ???

object DefaultInverse extends DefaultInverse
