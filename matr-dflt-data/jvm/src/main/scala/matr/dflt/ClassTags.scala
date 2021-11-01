/*
 * Copyright 2017-2020 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This work has been copied from the ZIO project and only adapted to the new Scala 3 syntax.
 *
 * Look here for the original content and context:
 * https://github.com/zio/zio/blob/master/core/jvm/src/main/scala/zio/ChunkPlatformSpecific.scala
 */

package matr.dflt:

   import scala.reflect.{classTag, ClassTag}

   object ClassTags:

      def fromValue[A](a: A): ClassTag[A] =
         val c = a.getClass
         val unboxedClass =
            if isBoolean(c) then BooleanClass.asInstanceOf[Class[A]]
            else if isByte(c) then ByteClass.asInstanceOf[Class[A]]
            else if isShort(c) then ShortClass.asInstanceOf[Class[A]]
            else if isInt(c) then IntClass.asInstanceOf[Class[A]]
            else if isLong(c) then LongClass.asInstanceOf[Class[A]]
            else if isFloat(c) then FloatClass.asInstanceOf[Class[A]]
            else if isDouble(c) then DoubleClass.asInstanceOf[Class[A]]
            else if isChar(c) then CharClass.asInstanceOf[Class[A]]
            else null
         if unboxedClass eq null then classTag[AnyRef].asInstanceOf[ClassTag[A]]
         else ClassTag(unboxedClass).asInstanceOf[ClassTag[A]]

      private def isBoolean(c: Class[?]): Boolean =
         c == BooleanClass || c == BooleanClassBox
      private def isByte(c: Class[?]): Boolean =
         c == ByteClass || c == ByteClassBox
      private def isShort(c: Class[?]): Boolean =
         c == ShortClass || c == ShortClassBox
      private def isInt(c: Class[?]): Boolean =
         c == IntClass || c == IntClassBox
      private def isLong(c: Class[?]): Boolean =
         c == LongClass || c == LongClassBox
      private def isFloat(c: Class[?]): Boolean =
         c == FloatClass || c == FloatClassBox
      private def isDouble(c: Class[?]): Boolean =
         c == DoubleClass || c == DoubleClassBox
      private def isChar(c: Class[?]): Boolean =
         c == CharClass || c == CharClassBox

      private val BooleanClass = classOf[Boolean]
      private val BooleanClassBox = classOf[java.lang.Boolean]
      private val ByteClass = classOf[Byte]
      private val ByteClassBox = classOf[java.lang.Byte]
      private val ShortClass = classOf[Short]
      private val ShortClassBox = classOf[java.lang.Short]
      private val IntClass = classOf[Int]
      private val IntClassBox = classOf[java.lang.Integer]
      private val LongClass = classOf[Long]
      private val LongClassBox = classOf[java.lang.Long]
      private val FloatClass = classOf[Float]
      private val FloatClassBox = classOf[java.lang.Float]
      private val DoubleClass = classOf[Double]
      private val DoubleClassBox = classOf[java.lang.Double]
      private val CharClass = classOf[Char]
      private val CharClassBox = classOf[java.lang.Character]
