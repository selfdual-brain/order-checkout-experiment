package com.selfdualbrain.hmrctest

/**
 * Signals product is outside pricing (or outside current products registry, if we have such).
 */
class UnknownProductsException(products: Seq[String]) extends Exception {

}
