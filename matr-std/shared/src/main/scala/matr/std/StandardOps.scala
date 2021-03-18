package matr.std:

  trait StandardOps extends StandardDoubleOps, 
                             StandardFloatOps, 
                             StandardIntOps,
                             StandardLongOps

  object StandardOps extends StandardOps
  