package matr.dflt

trait DefaultMatrixOps
    extends DefaultDeterminant,
      DefaultMatrixAddition,
      DefaultMatrixEquality,
      DefaultMatrixMultiplication,
      DefaultMatrixSubtraction,
      DefaultSubmatrix,
      DefaultTranspose,
      DefaultInverse

object DefaultMatrixOps extends DefaultMatrixOps
