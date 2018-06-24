import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Runs a play server on the default test port (Helpers.testServerPort == 19001).
 */
@RunWith(classOf[JUnitRunner])
class ServerSpec extends PlaySpec
  with BaseOneServerPerSuite
  with MyApplicationFactory
  with ScalaFutures
  with IntegrationPatience {

  private implicit val implicitPort = port

  "Server query should" should {
    "work" in {
      whenReady(play.api.test.WsTestClient.wsUrl("/").get) { response =>
        response.status mustBe play.api.http.Status.OK
      }
    }
  }
}

