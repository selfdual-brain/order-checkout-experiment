package com.selfdualbrain.hmrctest

/**
  * Common interface for checkout calculators.
  * Implementations reflect different total value calculation strategies.
  */
trait OrderCheckoutCalculator {

  /**
    * Calculates total value of an order.
    *
    * @param items an order given as list of string items (= names of products)
    * @return total money value to be paid by the customer
    */
  def calculateTotalValue(items: List[String]): Money

}
