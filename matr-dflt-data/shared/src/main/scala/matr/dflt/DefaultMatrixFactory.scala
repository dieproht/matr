package matr.dflt

import matr.Matrix
import matr.MatrixFactory
import matr.util.RowMajorIndex

import scala.collection.mutable
import scala.reflect.ClassTag

trait DefaultMatrixFactory:

   // new
   given createDefaultMatrixBuilder[R <: Int, C <: Int, T]: Matrix.CreateMatrixBuilder[R, C, T] =
         println("createDefaultMatrixBuilder")
         DefaultMatrixBuilder()

   // old
   given defaultMatrixFactory[R <: Int, C <: Int, T]
            (using Numeric[T], ClassTag[T])
            (using Matrix.Requirements.NonNegativeDimensions[R, C])
            (using ValueOf[R], ValueOf[C])
            : MatrixFactory[R, C, T] with
      def builder: Matrix.Builder[R, C, T] = DefaultMatrixBuilder()
    
object DefaultMatrixFactory extends DefaultMatrixFactory
