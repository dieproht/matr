package matr.std

import matr.ElementMultiplication
import matr.ElementDivision

trait StandardBigDecimalOps:

   given bigDecimalMultiplication: ElementMultiplication[BigDecimal, BigDecimal] with
      def times(lhs: BigDecimal, rhs: BigDecimal): Out = lhs * rhs
      type Out = BigDecimal

   given bigDecimalDoubleMultiplication: ElementMultiplication[BigDecimal, Double] with
      def times(lhs: BigDecimal, rhs: Double): Out = lhs * rhs
      type Out = BigDecimal

   given bigDecimalFloatMultiplication: ElementMultiplication[BigDecimal, Float] with
      def times(lhs: BigDecimal, rhs: Float): Out = lhs * rhs.toDouble
      type Out = BigDecimal

   given bigDecimalIntMultiplication: ElementMultiplication[BigDecimal, Int] with
      def times(lhs: BigDecimal, rhs: Int): Out = lhs * rhs
      type Out = BigDecimal

   given bigDecimalLongMultiplication: ElementMultiplication[BigDecimal, Long] with
      def times(lhs: BigDecimal, rhs: Long): Out = lhs * rhs
      type Out = BigDecimal

   given bigDecimalDivision: ElementDivision[BigDecimal, BigDecimal] with
      def dividedBy(lhs: BigDecimal, rhs: BigDecimal): Out = lhs / rhs
      type Out = BigDecimal

object StandardBigDecimalOps extends StandardBigDecimalOps
