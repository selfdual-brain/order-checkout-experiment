package com.selfdualbrain.hmrctest

class MoneyAmountSpec extends BaseSpec {

  "money amount" must "render correctly as string" in {
    Money(100).toString mustEqual "1.00"
    Money(0).toString mustEqual "0.00"
    Money(-1).toString mustEqual "-0.01"
    Money(1).toString mustEqual "0.01"
    Money(10).toString mustEqual "0.10"
    Money(-101).toString mustEqual "-1.01"
  }
}
