# matr [ˈmætə]

![Build status](https://github.com/dieproht/matr/actions/workflows/ci.yml/badge.svg)
[![Join the chat at https://gitter.im/dieproht/matr](https://badges.gitter.im/dieproht/matr.svg)](https://gitter.im/dieproht/matr?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)
![Maven Central](https://img.shields.io/maven-central/v/io.github.dieproht/matr-bundle_3.svg)

## A Scala 3 matrix library.

Matr is an attempt to bring together safety, flexibility and simplicity for matrix calculations. 

* Safety through typed shapes and immutable data
* Flexibility through loose coupling between interface, data and operations
* Simplicity through an easily accessible API and "batteries included" default implementations, both with zero dependencies

## Getting Started

Add the following dependency to your SBT build configuration:
```scala
libraryDependencies += "io.github.dieproht" %% "matr-bundle" % "<latest version>"
```

## Basic Usage

```scala
   // Bring API interfaces in scope
   import matr.{Matrix, MatrixFactory}
   // Bring bundled implementations in scope
   import matr.MatrBundle.given
   // Create a Matrix from tuples
   val a: Matrix[3, 4, Int] =
      MatrixFactory[3, 4, Int].fromTuple(
         (0, 8, 15, 0),
         (4, 7, 1, 1),
         (1, 2, 3, 4)
      )
   // Create a Matrix of ones
   val b: Matrix[3, 4, Int] = MatrixFactory[3, 4, Int].ones
   // Add two matrices
   val c: Matrix[3, 4, Int] = a + b
   // Create a Matrix of random numbers
   val d: Matrix[4, 2, Int] =
      MatrixFactory[4, 2, Int].tabulate((_, _) => scala.util.Random.nextInt(20))
   // Calculate the dot product of two Matrices
   val e: Matrix[3, 2, Int] = c dot d
   // Transpose a Matrix
   val f: Matrix[2, 3, Int] = e.transpose
   // Pretty print a Matrix
   println(f.mkString)
   // ... it outputs something like this:
   // ⎛ 73,  77, 116⎞
   // ⎝430, 253, 179⎠
```

Matr checks the shape of a Matrix at compile-time, thus the following code does *not* compile: 
```scala
   // Element (2, 3) is missing when creating the Matrix
   val ae =
      MatrixFactory[3, 4, Int].fromTuple(
         (0, 8, 15, 0),
         (4, 7, 1, 1),
         (1, 2, 3)
      )
    
   // Adding two Matrices with different dimensions is not possible
   val be = MatrixFactory[2, 5, Int].ones
   val ce = a + be

   // Column dimension of left-hande side and row dimension of right-hand side 
   // must be equal when calculating dot product
   val de = MatrixFactory[1, 2, Int].tabulate((_, _) => scala.util.Random.nextInt(20))
   val ee = c dot de
```

## Status of the Project

This project is currently in an early phase. If you are looking for a mature tool for matrix calculations in Scala, you should definitely consider [Breeze](https://github.com/scalanlp/breeze)!

## Copyright and License

All code is available to you under the Apache 2 license, available at
https://opensource.org/licenses/Apache-2.0. 

Copyright Karl F Walkow, 2020 - 2021. 
