package matr.dflt:

  import matr.MatrixEquality


  trait DefaultMatrixEquality:

    given defaultMatrixEquality[R <: Int, C <: Int, T]: MatrixEquality[R, C, T] with 

      def matricesEqual(lhs: M, rhs: M): Boolean = 
        lhs.fold(true){ (curr, rowIdx, colIdx) => 
          curr && lhs(rowIdx, colIdx) == rhs(rowIdx, colIdx) 
        }


  object DefaultMatrixEquality extends DefaultMatrixEquality
