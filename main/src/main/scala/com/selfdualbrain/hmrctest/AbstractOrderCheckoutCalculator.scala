package com.selfdualbrain.hmrctest

/**
  * Convenient place for pieces shared across different "checkout calculator" implementations.
  */
abstract class AbstractOrderCheckoutCalculator(currentPrices: Map[String, Money]) extends OrderCheckoutCalculator {

  protected def checkForUnknownProducts(items: List[String]): Unit = {
    //simplistic validation - we check that all products in the shopping cart are know (= they exist on the prices list)
    //if not, we fail with the UnknownProducts exception
    val unknownProducts = items.filterNot(x => currentPrices.contains(x))
    if (unknownProducts.nonEmpty)
      throw new UnknownProductsException(unknownProducts)
  }

}
