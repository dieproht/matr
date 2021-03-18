package matr.dflt:

  trait DefaultMatrixOps extends DefaultMatrixAddition, 
                                  DefaultMatrixEquality, 
                                  DefaultMatrixMultiplication, 
                                  DefaultMatrixSubtraction, 
                                  DefaultSubmatrix, 
                                  DefaultTranspose

  object DefaultMatrixOps extends DefaultMatrixOps
  