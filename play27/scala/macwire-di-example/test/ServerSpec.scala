import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play._
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.WsTestClient

import scala.concurrent.duration._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ServerSpec extends PlaySpec
  with BaseOneServerPerSuite
  with GreeterApplicationFactory
  with Results
  with ScalaFutures
  with IntegrationPatience {

  "Server query should" should {
    "work" in {
      WsTestClient.withClient { implicit client =>
        whenReady(wsUrl("/").get, timeout(10.seconds)) { response =>
          response.status mustBe OK
        }
      }
    }
  }
}

