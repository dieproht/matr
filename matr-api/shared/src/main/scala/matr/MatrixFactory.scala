package matr:

  import scala.compiletime.ops.any.==


  trait MatrixFactory[R <: Int, C <: Int, T]: 

    def builder: MatrixFactory.Builder[R, C, T]

    def rowMajor(elements: T*): Matrix[R, C, T]

    def tabulate(fillElem: (Int, Int) => T): Matrix[R, C, T]
    
    def zeros: Matrix[R, C, T]

    def ones: Matrix[R, C, T]

    def identity(using MatrixFactory.IsSquare[R, C] =:= true): Matrix[R, C, T]


  object MatrixFactory:

    trait Builder[R <: Int, C <: Int, T] extends Matrix[R, C, T]: 
 
      def update(rowIdx: Int, colIDx: Int, v: T): this.type
      
      def result: Matrix[R, C, T]


    def apply[R <: Int, C <: Int, T](using mf: MatrixFactory[R, C, T]): MatrixFactory[R, C, T] = mf


    type IsSquare[R <: Int, C <: Int] = R == C
