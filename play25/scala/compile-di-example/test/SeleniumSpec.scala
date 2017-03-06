import org.scalatestplus.play._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SeleniumSpec
  extends PlaySpec
    with OneServerPerTestWithMyComponents
    with OneBrowserPerTest
    with HtmlUnitFactory {

  "SeleniumSpec" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include ("Your new application is ready.")
    }
  }
}
