package com.selfdualbrain.hmrctest

/**
  * Simple implementation of money values.
  * Implemented as a value class so extremely lightweight in runtime.
  * We want to use Money (instead of Double, Float or BigDecimal) to have precise control over rounding.
  *
  * @param totalCents total value expressed as number of cents
  */
class Money(val totalCents: Int) extends AnyVal {

  def +(amount: Money): Money = Money(totalCents + amount.totalCents)

  def -(amount: Money): Money = Money(totalCents - amount.totalCents)

  def *(n: Int): Money = Money(totalCents * n)

  def *(a: Double): Money = Money(math.round(totalCents * a).toInt)

  override def toString: String = {
    val dollars = math.abs(totalCents) / 100
    val cents = math.abs(totalCents) % 100
    val centsAsString = cents.toString
    val leadingZeroIfNeeded = if (centsAsString.length == 1) "0" else ""
    val signIfNeeded = if (totalCents < 0) "-" else ""
    return signIfNeeded + dollars + "." + leadingZeroIfNeeded + centsAsString
  }
}

object Money {
  def apply(totalCents: Int) = new Money(totalCents)

  //Making Money to implement Numeric typeclass
  implicit object MoneyAsNumeric extends Numeric[Money] {
    override def plus(x: Money, y: Money): Money = x + y

    override def toDouble(x: Money): Double = x.totalCents.toDouble / 100

    override def toFloat(x: Money): Float = x.totalCents.toFloat / 100

    override def toInt(x: Money): Int = x.totalCents / 100

    override def negate(x: Money): Money = Money(-x.totalCents)

    override def fromInt(x: Int): Money = Money(x * 100)

    override def toLong(x: Money): Long = x.totalCents / 100

    override def times(x: Money, y: Money): Money = Money(x.totalCents * y.totalCents / 100)

    override def minus(x: Money, y: Money): Money = x - y

    override def compare(x: Money, y: Money): Int = x.totalCents - y.totalCents
  }

}

