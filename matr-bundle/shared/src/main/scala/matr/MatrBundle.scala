package matr:

  import matr.dflt.DefaultMatrixFactory
  import matr.dflt.DefaultMatrixOps
  import matr.std.StandardOps

  /** All-in-one import for batteries-included functionality. 
   * 
   * Just type ```import matr.MatrBundle.given``` to bring all out-of-the-box givens in scope.  
   */
  object MatrBundle extends DefaultMatrixFactory, DefaultMatrixOps, StandardOps
