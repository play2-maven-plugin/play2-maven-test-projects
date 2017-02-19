import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import play.api.Application
import play.api.inject.guice._
import org.scalatest.TestData
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends PlaySpec with OneBrowserPerTest with HtmlUnitFactory with GuiceOneServerPerSuite {

  "Application" should {

    "work from within a browser" in {
      go to ("http://localhost:" + port)

      pageSource must include ("Add Person")
    }
  }
}
