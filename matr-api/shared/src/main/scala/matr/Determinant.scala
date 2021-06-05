package matr: 

  trait Determinant[R <: Int, C <: Int, T](using Determinant.Validated[R, C]): 
    
    def det(m: M): T 

    type M = Matrix[R, C, T]

  
  object Determinant:
    
    type Validated[R <: Int, C <: Int] = Matrix.IsSquare[R, C] =:= true
