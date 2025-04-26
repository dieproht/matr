package matr.dflt

import matr.Matrix
import matr.Transpose

trait DefaultTranspose:

    given defaultTranspose[R <: Int, C <: Int, T](
        using
        ValueOf[R],
        ValueOf[C],
        Matrix.Requirements.NonNegativeDimensions[C, R]
    ): Transpose[R, C, T] =
        new Transpose:
            def transpose(m: Matrix[R, C, T]): Matrix[C, R, T] = DefaultTranspose.TransposeView(m)

object DefaultTranspose extends DefaultTranspose:

    final class TransposeView[OrigR <: Int, OrigC <: Int, T](orig: Matrix[OrigR, OrigC, T])(
        using
        ValueOf[OrigR],
        ValueOf[OrigC],
        Matrix.Requirements.NonNegativeDimensions[OrigC, OrigR]
    ) extends Matrix[OrigC, OrigR, T]:

        override def apply(rowIdx: Int, colIdx: Int): T =
            Matrix.Requirements.positionWithinShape(rowIdx, colIdx, rowDim, colDim)
            orig(colIdx, rowIdx)

        override def toString(): String = s"TransposeView(${orig.toString})"
