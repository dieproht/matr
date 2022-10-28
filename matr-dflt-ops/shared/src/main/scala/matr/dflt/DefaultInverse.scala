package matr.dflt

import matr.Determinant
import matr.Inverse
import matr.Matrix
import math.Numeric.Implicits.infixNumericOps
import matr.MatrixFactory
import matr.ElementDivision
import matr.ElementMultiplication
import scala.collection.mutable.ArrayBuffer

trait DefaultInverse:

   given defaultInverse[R <: Int, C <: Int, T]
            (using Matrix.Requirements.IsSquare[R, C])
            (using Numeric[T])
            (using ElementMultiplication.Aux[T, T, T])
            (using ElementDivision.Aux[T, T, T])
            (using MatrixFactory[R, C, T])
            : Inverse[R, C, T] with

      def inv(m: M): M =
         val num = summon[Numeric[T]]
         val mul = summon[ElementMultiplication.Aux[T, T, T]]
         val div = summon[ElementDivision.Aux[T, T, T]]
         val mf = summon[MatrixFactory[R, C, T]]

         val n = m.rowDim
         val a = m.modify
         val b = mf.identity.modify

         for j <- 0 until n do 
           var p = j
           while p < n && a(p, j) == num.zero do 
             p = p + 1
           if p == n then throw new ArithmeticException("Matrix nicht invertierbar!")
           if p != j then 
             swapRows(a, j, p)
             swapRows(b, j, p)
           val f = a(j, j)
           for k <- 0 until n do 
             a(j, k) = div.dividedBy(a(j, k), f)
             b(j, k) = div.dividedBy(b(j, k), f)
           for i <- 0 until n do 
             if i != j then
               val g = a(i, j)
               for k <- 0 until n do 
                 a(i, k) = a(i, k) - mul.times(g, a(j, k))
                 b(i, k) = b(i, k) - mul.times(g, b(j, k))

         b.result

      private def swapRows(mb: MatrixFactory.Builder[R, C, T], r1: Int, r2: Int): Unit = 
        for idx <- 0 until mb.colDim do 
          val b = mb(r1, idx)
          mb(r1, idx) = mb(r2, idx)
          mb(r2, idx) = b


object DefaultInverse extends DefaultInverse

/*


  // Berechnung: 
  	
  for (int j=0; j<n; j++) {                                 // Für alle Spaltenindizes ...
    int p = j;                                              // Variable für Zeilenindex
    while (p < n && a[p][j] == 0) p++;                      // Index erhöhen, bis Spaltenelement ungleich 0
    if (p == n) {                                           // Falls Suche erfolglos ...
      System.out.println("Matrix nicht invertierbar!");     // Fehlermeldung
      return null;                                          // Rückgabewert
      } 
    if (p != j) {                                           // Falls Zeilentausch nötig ...
      double[] temp = a[j];                                 // Zeile von Matrix a mit Index j
      a[j] = a[p];                                          // Zeile mit Index j neu
      a[p] = temp;                                          // Zeile mit Index p neu
      temp = b[j];                                          // Zeile von Matrix b mit Index j
      b[j] = b[p];                                          // Zeile mit Index j neu
      b[p] = temp;                                          // Zeile mit Index p neu
      }
    double f = a[j][j];                                     // Element der Hauptdiagonale
    for (int k=0; k<n; k++) {                               // Für alle Spaltenindizes ...
      a[j][k] /= f;                                         // In Matrix a durch f dividieren
      b[j][k] /= f;                                         // In Matrix b durch f dividieren
      } 
    for (int i=0; i<n; i++) {                               // Für alle Zeilenindizes ...
      if (i == j) continue;                                 // Element der Hauptdiagonale überspringen
      f = a[i][j];                                          // Faktor für Multiplikation
      for (int k=0; k<n; k++) {                             // Für alle Spaltenindizes ..
        a[i][k] -= f*a[j][k];                               // Zeilenumformung in Matrix a
        b[i][k] -= f*b[j][k];                               // Zeilenumformung in Matrix b
        }
      }
    }
  	
  return b; 

  */
