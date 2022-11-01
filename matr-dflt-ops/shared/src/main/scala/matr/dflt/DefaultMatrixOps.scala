package matr.dflt

trait DefaultMatrixOps
    extends DefaultDeterminant,
      DefaultInverse,
      DefaultMatrixAddition,
      DefaultMatrixEquality,
      DefaultMatrixMultiplication,
      DefaultMatrixSubtraction,
      DefaultSubmatrix,
      DefaultTranspose

object DefaultMatrixOps extends DefaultMatrixOps
