import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play._
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{Injecting, WsTestClient}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ServerSpec extends PlaySpec
  with BaseOneServerPerSuite
  with GreeterApplicationFactory
  with Results
  with ScalaFutures {

  "Server query should" should {

    "work" in {
      WsTestClient.withClient { implicit client =>
        whenReady(wsUrl("/").get) { response =>
          response.status mustBe OK
        }
      }
    }
  }
}

