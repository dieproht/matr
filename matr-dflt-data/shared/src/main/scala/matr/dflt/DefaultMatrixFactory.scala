package matr.dflt

import matr.Matrix
import matr.MatrixFactory
import matr.util.MatrixFactoryCache

import scala.reflect.ClassTag

trait DefaultMatrixFactory:

   given defaultMatrixFactory[R <: Int, C <: Int, T]
            (using Numeric[T], ClassTag[T])
            (using Matrix.Requirements.NonNegativeDimensions[R, C])
            (using ValueOf[R], ValueOf[C])
            : MatrixFactory[R, C, T] = MatrixFactoryCache[R, C, T](
      new MatrixFactory[R, C, T]:
         override def builder: Matrix.Builder[R, C, T] = DefaultMatrixBuilder()
   )

object DefaultMatrixFactory extends DefaultMatrixFactory
