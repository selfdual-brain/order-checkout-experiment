package com.selfdualbrain.hmrctest

class BonusItemsAwareCalculatorTest extends BaseSpec {

  trait CommonSetup {
    val calc = new BonusItemsAwareCalculator(
      currentPrices = Map("apple" -> Money(60), "orange" -> Money(25), "melon" -> Money(100)),
      bonusPolicy = Map("apple" -> (2,1), "orange" -> (3,2)) ////for each apple get one extra apple free, get 3 oranges for the price of 2, no bonus for melons
    )
  }

  "Calculator" must "correctly apply bonus offer" in new CommonSetup {
    calc.calculateTotalValue(List("apple", "apple", "orange", "apple", "orange", "orange", "orange")) mustEqual Money(195)
    calc.calculateTotalValue(List("apple", "apple", "orange", "apple", "orange", "melon", "orange", "orange")) mustEqual Money(295)
  }

  it must "perform correctly with one-element shopping list" in new CommonSetup {
    calc.calculateTotalValue(List("apple")) mustEqual Money(60)
    calc.calculateTotalValue(List("apple", "orange", "melon")) mustEqual Money(185)
  }

  it must "perform correctly with duplications" in new CommonSetup {
    calc.calculateTotalValue(List("apple", "apple")) mustEqual Money(60)
    calc.calculateTotalValue(List("apple", "orange", "apple")) mustEqual Money(85)
  }

  it must "do not fail with empty order" in new CommonSetup {
    calc.calculateTotalValue(List()) mustEqual Money(0)
  }

  it must "fail for unknown product" in new CommonSetup {
    intercept[UnknownProductsException] {
      calc.calculateTotalValue(List("apple", "orange", "avocado"))
    }
  }

}
