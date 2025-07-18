package matr

import org.scalatest.NonImplicitAssertions
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

trait MatrFlatSpec
    extends AnyFlatSpec,
      should.Matchers,
      ScalaCheckDrivenPropertyChecks,
      NonImplicitAssertions
