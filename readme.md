API of order checkout calculators is defined in **OrderCheckoutCalculator**.
There are 3 implementaionts provided:
* **SimpleOrderCheckoutCalculator** - this one is just summing prices
* **BonusItemsAwareCalculator** - this one supports simple dicounts support
* **DiscountAwareOrderCheckoutCalculator** - this one supports advanced discounts support

This implementation is a drill only. It is nowhere near close to a real "checkout" problem, where one would
have to start with a solid model of products and prices, plus the fact that there are products you measure
with "weight" (as opposed to "number of items").
