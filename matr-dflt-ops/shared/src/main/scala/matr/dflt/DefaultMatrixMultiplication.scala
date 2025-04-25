package matr.dflt

import matr.ElementMultiplication
import matr.MatrixFactory
import matr.MatrixMultiplication

trait DefaultMatrixMultiplication:

    given defaultMatrixMultiplication[R <: Int, C <: Int, L <: Int, T, U, X](
        using
        ElementMultiplication.Aux[T, U, X],
        Numeric[X],
        MatrixFactory[R, L, X]
    )(using
        ValueOf[R],
        ValueOf[C],
        ValueOf[L]
    )
        : MatrixMultiplication[R, C, L, T, U, X] =
        new MatrixMultiplication:

            def dot(lhs: Lhs, rhs: Rhs): Out =
                val elemMul = summon[ElementMultiplication.Aux[T, U, X]]
                val numX = summon[Numeric[X]]
                val resultNumCols = valueOf[L]
                val buildr = MatrixFactory[R, L, X].builder
                var j: Int = 0 // lhs col
                while j < lhs.colDim do
                    var k: Int = 0 // result row
                    while k < lhs.rowDim do
                        var l: Int = 0 // result col
                        while l < resultNumCols do
                            if j == 0 then
                                buildr(k, l) = elemMul.times(lhs(k, j), rhs(j, l))
                            else
                                buildr(k, l) = numX.plus(buildr(k, l), elemMul.times(lhs(k, j), rhs(j, l)))
                            l = l + 1
                        k = k + 1
                    j = j + 1
                buildr.result

object DefaultMatrixMultiplication extends DefaultMatrixMultiplication
