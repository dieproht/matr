package matr.dflt

import matr.Matrix
// import matr.MatrixContext
import matr.MatrixFactory
import matr.util.RowMajorIndex

import scala.collection.mutable
import scala.reflect.ClassTag

trait DefaultMatrixFactory:

   // new
//    given newDefaultMatrixBuilder[R <: Int, C <: Int, T]: MatrixContext[R, C, T] =
//          println("build DefaultMatrixBuilder")
//          DefaultMatrixBuilder()

   // old
   given defaultMatrixFactory[R <: Int, C <: Int, T]
            (using Numeric[T], ClassTag[T])
            (using Matrix.Requirements.NonNegativeDimensions[R, C])
            (using ValueOf[R], ValueOf[C])
            : MatrixFactory[R, C, T] with
      def builder: Matrix.Builder[R, C, T] = DefaultMatrixBuilder()
    
object DefaultMatrixFactory extends DefaultMatrixFactory
