package matr.dflt

import matr.Matrix
import matr.util.RowMajorIndex

import scala.collection.mutable
import scala.reflect.ClassTag

case class DefaultMatrixBuilder[R <: Int, C <: Int, T]()(
    using
    ValueOf[R],
    ValueOf[C],
    Numeric[T],
    Matrix.Requirements.NonNegativeDimensions[R, C]
) extends Matrix.Builder[R, C, T]:

    private val num: Numeric[T] = summon[Numeric[T]]

    private var elemMap: mutable.Map[(Int, Int), T] = mutable.Map.empty

    private var elemArr: Array[T] = null

    private val treshold: Int = (rowDim * colDim * DefaultMatrixBuilder.MIN_DENSE_FILL).floor.toInt

    override def apply(rowIdx: Int, colIdx: Int): T =
        if elemMap ne null then
            elemMap.getOrElse((rowIdx, colIdx), num.zero)
        else
            elemArr(RowMajorIndex.toIdx(rowIdx, colIdx, valueOf[C]))

    override def update(rowIdx: Int, colIdx: Int, v: T): this.type =
        if (elemMap ne null) && (elemMap.size < treshold) then
            if v != num.zero then elemMap((rowIdx, colIdx)) = v
        else
            if elemArr eq null then
                given ClassTag[T] = ClassTag(v.getClass)
                elemArr = Array.fill(rowDim * colDim)(num.zero)
                elemMap.foreachEntry((mk, mv) => elemArr(RowMajorIndex.toIdx(mk._1, mk._2, colDim)) = mv)
                elemMap = null
            elemArr(RowMajorIndex.toIdx(rowIdx, colIdx, valueOf[C])) = v
        this

    override def result: Matrix[R, C, T] =
        if elemArr eq null then
            new DefaultSparseMatrix[R, C, T](elemMap.toMap)
        else
            new DefaultDenseMatrix[R, C, T](elemArr)

object DefaultMatrixBuilder:
    val MIN_DENSE_FILL: Float = 0.5
