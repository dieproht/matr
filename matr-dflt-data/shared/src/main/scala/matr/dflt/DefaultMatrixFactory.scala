package matr.dflt: 

  import matr.MatrixFactory
  import matr.Matrix
  import scala.collection.mutable
  import scala.reflect.ClassTag

  trait DefaultMatrixFactory: 
    
    given defaultMatrixFactory[R <: Int, C <: Int, T]
                              (using num: Numeric[T])
                              (using Matrix.DimsOK[R, C] =:= true)
                              (using vr: ValueOf[R], vc: ValueOf[C])
                              : MatrixFactory[R, C, T] with

      def builder: MatrixFactory.Builder[R, C, T] = 
        new DefaultMatrixFactory.Builder[R, C, T]


      def rowMajor(elements: T*): Matrix[R, C, T] =
        require(elements.length == vr.value * vc.value, 
          s"Size of given element collection must be ${vr.value * vc.value}, but was ${elements.size}")
        val buildr: MatrixFactory.Builder[R, C, T] = builder
        var idx: Int = 0
        while (idx < elements.length) do 
          val (rowIdx, colIdx) = RowMajorIndex.fromIdx(idx, vc.value)
          buildr(rowIdx, colIdx) = elements(idx)
          idx = idx + 1
        buildr.result


      def tabulate(fillElem: (Int, Int) => T): Matrix[R, C, T] = 
        val buildr: MatrixFactory.Builder[R, C, T] = builder
        buildr.iterate{ (rowIdx, colIdx) => 
          buildr(rowIdx, colIdx) = fillElem(rowIdx, colIdx) 
        }
        buildr.result


      def zeros: Matrix[R, C, T] = 
        tabulate((_, _) => num.zero)

      
      def ones: Matrix[R, C, T] =
        tabulate((_, _) => num.one)


      def identity(using Matrix.IsSquare[R, C] =:= true): Matrix[R, C, T] =
        tabulate { (rowIdx, colIdx) => 
          if rowIdx == colIdx then num.one else num.zero
        }

  
    class Builder[R <: Int, C <: Int, T]
                 (using num: Numeric[T])
                 (using Matrix.DimsOK[R, C] =:= true)
                 (using vr: ValueOf[R], vc: ValueOf[C])
                 extends MatrixFactory.Builder[R, C, T], Matrix[R, C, T]:

      private var elemMap: mutable.Map[(Int, Int), T] = mutable.Map.empty

      private var elemArr: Array[T] = null

      private val treshold: Int = (vr.value * vc.value * MIN_DENSE_FILL).floor.toInt 

      override def apply(rowIdx: Int, colIdx: Int): T = 
        if elemMap ne null then
          elemMap.getOrElse((rowIdx, colIdx), num.zero)
        else
          elemArr(RowMajorIndex.toIdx(rowIdx, colIdx, valueOf[C]))

      override def update(rowIdx: Int, colIdx: Int, v: T): this.type = 
        if (elemMap ne null) && (elemMap.size < treshold) then
          if v != num.zero then
            elemMap((rowIdx, colIdx)) = v
        else 
          if elemArr eq null then
            val tag: ClassTag[T] = ClassTags.fromValue(v)
            given ClassTag[T] = tag
            elemArr = Array.fill(vr.value * vc.value)(num.zero)
            elemMap.foreachEntry((mk, mv) => elemArr(RowMajorIndex.toIdx(mk._1, mk._2, vc.value)) = mv)
            elemMap = null
          elemArr(RowMajorIndex.toIdx(rowIdx, colIdx, valueOf[C])) = v
        this

      override def result: Matrix[R, C, T] = 
        if elemArr eq null then
          new DefaultSparseMatrix[R, C, T](elemMap.toMap)
        else
          new DefaultDenseMatrix[R, C, T](elemArr)


    private val MIN_DENSE_FILL: Float = 0.5  



  object DefaultMatrixFactory extends DefaultMatrixFactory
  