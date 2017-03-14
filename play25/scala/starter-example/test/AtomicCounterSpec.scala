import org.scalatestplus.play._

import services.AtomicCounter

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/** A very simple unit-test example. */
@RunWith(classOf[JUnitRunner])
class AtomicCounterSpec extends PlaySpec {

  "AtomicCounter" should {

    "produce increasing values" in {
      val counter: AtomicCounter = new AtomicCounter
      counter.nextCount() mustBe 0
      counter.nextCount() mustBe 1
      counter.nextCount() mustBe 2
    }
  }
}
