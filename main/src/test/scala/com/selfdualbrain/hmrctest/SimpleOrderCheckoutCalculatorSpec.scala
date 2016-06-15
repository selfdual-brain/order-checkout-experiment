package com.selfdualbrain.hmrctest

class SimpleOrderCheckoutCalculatorSpec extends BaseSpec {

  trait CommonSetup {
    val currentPrices: Map[String, Money] = Map("apple" -> Money(60), "orange" -> Money(25), "melon" -> Money(100))
    val calculator: SimpleOrderCheckoutCalculator = new SimpleOrderCheckoutCalculator(currentPrices)
  }

  "Calculator" must "do not fail with empty order" in new CommonSetup {
    calculator.calculateTotalValue(List()) mustEqual Money(0)
  }

  it must "fail for unknown product" in new CommonSetup {
    intercept[UnknownProductsException] {
      calculator.calculateTotalValue(List("apple", "orange", "avocado"))
    }
  }

  it must "perform correctly with one-element shopping list" in new CommonSetup {
    calculator.calculateTotalValue(List("apple")) mustEqual Money(60)
  }

  it must "perform correctly with no-duplications" in new CommonSetup {
    calculator.calculateTotalValue(List("apple", "orange", "melon")) mustEqual Money(185)
  }

  it must "perform correctly with duplications" in new CommonSetup {
    calculator.calculateTotalValue(List("apple", "apple")) mustEqual Money(120)
    calculator.calculateTotalValue(List("apple", "orange", "apple")) mustEqual Money(145)
  }

}
