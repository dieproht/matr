package matr.dflt:

  trait DefaultMatrixOps extends DefaultDeterminant, 
                                 DefaultMatrixAddition, 
                                 DefaultMatrixEquality, 
                                 DefaultMatrixMultiplication, 
                                 DefaultMatrixSubtraction, 
                                 DefaultSubmatrix, 
                                 DefaultTranspose

  object DefaultMatrixOps extends DefaultMatrixOps
  