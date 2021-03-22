package matr:

  import matr.dflt.DefaultMatrixFactory
  import matr.dflt.DefaultMatrixOps
  import matr.std.StandardOps

  /** All-in-one import for batteries-included functionality. 
   * 
   * Just type ```import matr.MatrBundle.given``` and you can start! 
   */
  object MatrBundle extends DefaultMatrixFactory, DefaultMatrixOps, StandardOps
