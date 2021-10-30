package matr.std:

   trait StandardOps
       extends StandardBigDecimalOps,
         StandardDoubleOps,
         StandardFloatOps,
         StandardIntOps,
         StandardLongOps

   object StandardOps extends StandardOps
