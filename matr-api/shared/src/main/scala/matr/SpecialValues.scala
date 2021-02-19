package matr:

  trait SpecialValues[A]:

    def isZero(value: A): Boolean

    def zero: A

    def one: A
