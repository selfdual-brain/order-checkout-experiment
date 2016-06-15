package com.selfdualbrain.hmrctest

/**
 * Minimalistic solution. Simple, yet easy to evolve.
 */
class SimpleOrderCheckoutCalculator(currentPrices: Map[String, Money]) extends AbstractOrderCheckoutCalculator(currentPrices) {

  def calculateTotalValue(items: List[String]): Money = {
    this.checkForUnknownProducts(items)

    //expression below works because Money "extends" Numeric typeclass (!)
    return (items map currentPrices).sum
  }

}
