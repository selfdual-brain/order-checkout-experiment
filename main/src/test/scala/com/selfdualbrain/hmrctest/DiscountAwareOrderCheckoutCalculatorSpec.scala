package com.selfdualbrain.hmrctest

class DiscountAwareOrderCheckoutCalculatorSpec extends BaseSpec {

  trait CommonSetup {
    val currentPrices: Map[String, Money] = Map("apple" -> Money(60), "orange" -> Money(25), "melon" -> Money(100))

    val currentDiscounts: Map[String, (Int, Money) => Money] = Map(
      //get 3 apples for the price of two
      "apple" -> { (amount: Int, price: Money) => price * (amount / 3) },

      //get 10% discount when you order 10 oranges or more
      "orange" -> { (amount: Int, price: Money) => if (amount < 10) Money(0) else (price * amount) * 0.1 }
    )

    val calc: OrderCheckoutCalculator = new DiscountAwareOrderCheckoutCalculator(currentPrices, currentDiscounts)
  }

  "Calculator" must "perform correctly with one-element shopping list" in new CommonSetup {
    calc.calculateTotalValue(List("apple")) mustEqual Money(60)
    calc.calculateTotalValue(List("apple", "orange", "melon")) mustEqual Money(185)
  }

  it must "perform correctly with duplications" in new CommonSetup {
    calc.calculateTotalValue(List("apple", "apple")) mustEqual Money(120)
    calc.calculateTotalValue(List("apple", "orange", "apple")) mustEqual Money(145)
  }

  it must "do not fail with empty order" in new CommonSetup {
    calc.calculateTotalValue(List()) mustEqual Money(0)
  }

  it must "correctly apply discount for apples" in new CommonSetup {
    calc.calculateTotalValue(List("apple", "apple", "apple")) mustEqual Money(120)
    calc.calculateTotalValue(List("apple", "apple", "apple", "apple")) mustEqual Money(180)
  }

  it must "correctly apply discount for oranges" in new CommonSetup {
    calc.calculateTotalValue(List.fill(9)("orange")) mustEqual Money(225)
    calc.calculateTotalValue(List.fill(10)("orange")) mustEqual Money(225)
    calc.calculateTotalValue(List.fill(11)("orange")) mustEqual Money(247) //rounding happens here !
  }

  it must "correctly apply two discounts at the same time" in new CommonSetup {
    calc.calculateTotalValue(List.fill(11)("orange") ++ List("apple", "apple", "melon", "apple")) mustEqual Money(467) //rounding happens here !
  }

  it must "fail for unknown product" in new CommonSetup {
    intercept[UnknownProductsException] {
      calc.calculateTotalValue(List("apple", "orange", "avocado"))
    }
  }

}
