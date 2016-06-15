package com.selfdualbrain.hmrctest

/**
  * Checkout calculator implementation that supports very simple discounts feature.
  * Applicable for offers like "get 3 avocados for the price of 2".
  *
  * Bonus policy encoded as Map("avocado" -> (3,2)), this reads as "if you buy avocados,
  * every group of 3 such items you will get for the price of 2 items".
  */
class BonusItemsAwareCalculator(currentPrices: Map[String, Money], bonusPolicy: Map[String, (Int, Int)]) extends AbstractOrderCheckoutCalculator(currentPrices) {

  override def calculateTotalValue(items: List[String]): Money = {
    this.checkForUnknownProducts(items)

    val product2amount: Map[String, Int] = items groupBy identity mapValues (_.length)
    val productValues: Iterable[Money] = for {
      (product, amount) <- product2amount
      originalMoneyValue: Money = currentPrices(product) * amount
      discount: Money = bonusPolicy.get(product) match {
        case None => Money(0)
        case Some((actual, effective)) => currentPrices(product) * (amount / actual) * (actual - effective)
      }
    } yield originalMoneyValue - discount

    return productValues.sum
  }

}
