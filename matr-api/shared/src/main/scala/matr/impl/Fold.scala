package matr.impl

import matr.Matrix

private[matr] object Fold:

    inline def apply[R <: Int, C <: Int, T, S](m: Matrix[R, C, T], start: S, op: (S, Int, Int) => S)
        : S =
        var state: S = start
        var rowIdx: Int = 0
        while rowIdx < m.rowDim do
            var colIdx: Int = 0
            while colIdx < m.colDim do
                state = op(state, rowIdx, colIdx)
                colIdx = colIdx + 1
            rowIdx = rowIdx + 1
        state
