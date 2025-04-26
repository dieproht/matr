package matr.dflt

import matr.Matrix
import matr.MatrixFactory
import matr.util.MatrixFactoryCache

import scala.reflect.ClassTag

trait DefaultMatrixFactory:

    given defaultMatrixFactory[R <: Int, C <: Int, T](
        using
        ValueOf[R],
        ValueOf[C],
        Numeric[T],
        ClassTag[T],
        Matrix.Requirements.NonNegativeDimensions[R, C]
    ): MatrixFactory[R, C, T] =
        MatrixFactoryCache[R, C, T](
          new MatrixFactory[R, C, T]:
              override def builder: Matrix.Builder[R, C, T] = DefaultMatrixBuilder()
        )

object DefaultMatrixFactory extends DefaultMatrixFactory
