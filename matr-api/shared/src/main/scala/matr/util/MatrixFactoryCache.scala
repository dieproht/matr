package matr.util

import matr.MatrixFactory

import scala.collection.concurrent.TrieMap
import scala.reflect.ClassTag

/** Provides a cached lookup of MatrixFactories.
  *
  * Implementations of `MatrixFactory` should use it to avoid creating a new `MatrixFactory` each time a new
  * `Matrix.Builder` is created.
  */
object MatrixFactoryCache:

    private val cache: TrieMap[String, MatrixFactory[? <: Int, ? <: Int, ?]] = TrieMap()

    private def cacheKey[R <: Int, C <: Int, T](using ValueOf[R], ValueOf[C], ClassTag[T]): String =
        s"${valueOf[R]}-${valueOf[C]}-${summon[ClassTag[T]].runtimeClass.getName}"

    def apply[R <: Int, C <: Int, T](mf: => MatrixFactory[R, C, T])(
        using
        ValueOf[R],
        ValueOf[C],
        ClassTag[T]
    ): MatrixFactory[R, C, T] = //
        cache.getOrElseUpdate(cacheKey[R, C, T], mf).asInstanceOf[MatrixFactory[R, C, T]]
