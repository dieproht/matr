package matr:

  import scala.compiletime.ops.any.==

  /** Central entry point for creating Matrices. 
    * Modules implementing the `Matrix` trait should also provide an instance of this type class. 
    */ 
  trait MatrixFactory[R <: Int, C <: Int, T]: 

    /** Returns a new `Builder` instance. 
      */
    def builder: MatrixFactory.Builder[R, C, T]

    /** Creates a Matrix containing the specified elements, assuming row-major order. 
      * Dimensions will be checked at runtime. 
      */ 
    def rowMajor(elements: T*): Matrix[R, C, T]

    /** Creates a Matrix containing the elements returned by the specified function. 
      * 
      * @param fillElem function returning the element at the specified position (row index, column index)
      */ 
    def tabulate(fillElem: (Int, Int) => T): Matrix[R, C, T]
    
    /** Creates a Matrix of zeros. 
      */ 
    def zeros: Matrix[R, C, T]

    /** Creates a Matrix of ones. 
      */ 
    def ones: Matrix[R, C, T]

    /** Creates the identity Matrix. 
      */ 
    def identity(using Matrix.IsSquare[R, C] =:= true): Matrix[R, C, T]


  object MatrixFactory:

    /** A `Builder` is a "Matrix under construction" that itself forms a (mutable) Matrix. 
      * Utilizing `Builder` is the preferred way of creating new Matrices in Matrix operations. 
      * 
      * _One_ instance of a `Builder` is used to create _one_ Matrix. This means that invoking the 
      * same `Builder` instance multiple times is OK, but letting a `Builder` leave a local scope 
      * is not OK (it's mutable!). 
      * 
      * Implementations of this trait must return zero for uninitialized element positions. 
      */ 
    trait Builder[R <: Int, C <: Int, T] extends Matrix[R, C, T]: 
 
      /** Sets the specified element at the specified position. 
        */ 
      def update(rowIdx: Int, colIdx: Int, v: T): this.type
      
      /** Creates the Matrix resulting from this `Builder`. 
        */ 
      def result: Matrix[R, C, T]

    /** Returns the implicitly available `MatrixFactory`. 
      */ 
    def apply[R <: Int, C <: Int, T](using mf: MatrixFactory[R, C, T]): MatrixFactory[R, C, T] = mf
