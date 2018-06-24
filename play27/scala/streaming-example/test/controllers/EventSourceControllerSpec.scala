package controllers

import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.duration._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Integration test to run through event source.
 */
@RunWith(classOf[JUnitRunner])
class EventSourceControllerSpec extends PlaySpec
    with GuiceOneAppPerSuite
    with ScalaFutures
{
  "event source controller" should {
    "return OK through route" in {
      val request = FakeRequest(method = GET, path = "/scala/eventSource/liveClock")
      route(app, request) match {
        case Some(future) =>
          whenReady(future, timeout(10 seconds)) { result =>
            result.header.status mustEqual(OK)
          }
        case None =>
          fail
      }
    }
  }
}
