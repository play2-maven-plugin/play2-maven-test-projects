package utils

import java.util.Random

import org.scalatest.{MustMatchers, WordSpecLike}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FakeStockQuoteSpec extends WordSpecLike with MustMatchers {

  "newPrice" should {

    "be plus or minus 5% of old price" in {
      val stockQuote = new FakeStockQuote()
      val origPrice = new Random().nextDouble()
      val newPrice = stockQuote.newPrice(origPrice)

      newPrice mustBe >=(origPrice - (origPrice * 0.05))
      newPrice mustBe <(origPrice + (origPrice * 0.05))
    }
  }

}
