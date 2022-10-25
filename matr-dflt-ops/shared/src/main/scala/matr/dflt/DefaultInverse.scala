package matr.dflt

import matr.Determinant
import matr.Inverse
import matr.Matrix
import math.Numeric.Implicits.infixNumericOps
import matr.MatrixFactory
import matr.ElementDivision
import matr.ElementMultiplication

trait DefaultInverse:

   given defaultInverse[R <: Int, C <: Int, T]
            (using Matrix.Requirements.IsSquare[R, C])
            (using Numeric[T])
            (using ElementMultiplication.Aux[T, T, T])
            (using ElementDivision.Aux[T, T, T])
            (using MatrixFactory[R, C, T])
            : Inverse[R, C, T] with

      def inv(m: M): M =
         val num = summon[Numeric[T]]
         val mul = summon[ElementMultiplication.Aux[T, T, T]]
         val div = summon[ElementDivision.Aux[T, T, T]]
         val mf = summon[MatrixFactory[R, C, T]]

         val mm = m.modify
         val mr = mf.identity.modify
         val dim = m.rowDim

         for i <- 0 until dim do
         // TODO  
         //   If Ai,i = 0
	      //     Print "Mathematical Error!"
	      //     Stop
	      //   End If
           for j <- 0 until dim do 
             if i != j then 
               val ratio = div.dividedBy(m(j,i), m(i,i))
               for k <- 0 until dim do
                 mm(j, k) = mm(j, k) - mul.times(ratio, mm(i, k))
                 mr(j, k) = mr(j, k) - mul.times(ratio, mr(i, k))

         for i <- 0 until dim do
           for j <- 0 until dim do
             mr(i, j) = div.dividedBy(mr(i, j), mr(i, i))  

         mr.result

object DefaultInverse extends DefaultInverse
