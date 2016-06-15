package com.selfdualbrain.hmrctest

/**
  * Checkout calculator implementation that supports advanced discounts feature.
  * Applicabie for pretty much any type of offer, like "if you buy 2 avocados, the next one if available for half price",
  * or even much more exotic offers like "if you buy milk, each subsequent pack is 10% cheaper then the previous one, up to 10 packs allowed".
  *
  * We represent each discount logic as a function (Int,MoneyAmount) => MoneyAmount
  * First argument is total amount of product ordered and second argument is current price of this product.
  * The discount function tells how much discount a customer will get.
  *
  * Example 1:
  *
  * for offer "get 3 apples for the price of two" the discount function will look like:
  *   (amount: Int, price: Money) => price * (amount/3)
  *
  * Example 2:
  *
  * for offer "get 10% discount when you order 10 oranges or more" the discount function will look like:
  *   (amount: Int, price: Money) => if (amount < 10) Money(0) else (price * amount) * 0.1
  */
class DiscountAwareOrderCheckoutCalculator(currentPrices: Map[String, Money], currentDiscounts: Map[String, (Int, Money) => Money]) extends AbstractOrderCheckoutCalculator(currentPrices) {

  def calculateTotalValue(items: List[String]): Money = {
    this.checkForUnknownProducts(items)

    val product2amount: Map[String, Int] = items groupBy identity mapValues (_.length)
    val productValues: Iterable[Money] = for {
      (product, amount) <- product2amount
      originalMoneyValue: Money = currentPrices(product) * amount
      discount: Money = currentDiscounts.get(product) match {
        case None => Money(0)
        case Some(discountFunction) => discountFunction(amount, currentPrices(product))
      }
    } yield originalMoneyValue - discount

    return productValues.sum
  }

}
