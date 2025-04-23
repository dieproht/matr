package matr.util

import matr.MatrFlatSpec
import matr.Matrix
import matr.MatrixFactory

/** Test that Matrix Factories are created for different dimensions/data types. Because the cache is
  * global, test dimensions are chosen not to infer with other tests.
  */
class MatrixFactoryCacheSpec extends MatrFlatSpec:

   it should "create one factory when invoked one time" in:
      var cnt: Int = 0
      def inc(): Unit = cnt = cnt + 1
      MatrixFactoryCache(MockedMatrixFactory[31, 41, Int](inc))
      cnt shouldEqual 1

   it should "create two factories when invoked with different data types" in:
      var cnt: Int = 0
      def inc(): Unit = cnt = cnt + 1
      MatrixFactoryCache(MockedMatrixFactory[22, 12, Int](inc))
      MatrixFactoryCache(MockedMatrixFactory[22, 12, Double](inc))
      cnt shouldEqual 2

   it should "create two factories when invoked with different dimensions" in:
      var cnt: Int = 0
      def inc(): Unit = cnt = cnt + 1
      MatrixFactoryCache(MockedMatrixFactory[13, 33, Int](inc))
      MatrixFactoryCache(MockedMatrixFactory[33, 43, Int](inc))
      cnt shouldEqual 2

   it should
      "create two factories when invoked multiple times with two different dimensions/data types" in:
         var cnt: Int = 0
         def inc(): Unit = cnt = cnt + 1
         MatrixFactoryCache(MockedMatrixFactory[14, 34, Int](inc))
         MatrixFactoryCache(MockedMatrixFactory[34, 44, Double](inc))
         MatrixFactoryCache(MockedMatrixFactory[14, 34, Int](inc))
         MatrixFactoryCache(MockedMatrixFactory[34, 44, Double](inc))
         MatrixFactoryCache(MockedMatrixFactory[34, 44, Double](inc))
         cnt shouldEqual 2

   class MockedMatrixFactory[R <: Int, C <: Int, T]
            (factoryCreated: () => Unit)
            (using ValueOf[R], ValueOf[C], Numeric[T])
       extends MatrixFactory[R, C, T]:
      factoryCreated()
      override def builder: Matrix.Builder[R, C, T] = ???
