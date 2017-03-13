package controllers

import actors.TestKitSpec
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Keep
import akka.stream.testkit.scaladsl.{TestSink, TestSource}
import akka.testkit.TestProbe
import org.scalatest.MustMatchers
import play.api.{ApplicationLoader, Environment, Mode}
import play.api.http.DefaultHttpErrorHandler
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.ExecutionContext

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HomeControllerSpec extends TestKitSpec with MustMatchers {

  "createWebSocketFlow" should {

    "create a websocket flow and send a message through" in {
      implicit val materializer = ActorMaterializer()(system)
      implicit val ec: ExecutionContext = system.dispatcher

      val classLoader = ApplicationLoader.getClass.getClassLoader
      val env = new Environment(new java.io.File("."), classLoader, Mode.Test)
      val context = ApplicationLoader.createContext(env)
      val config = context.initialConfiguration
      val errorHandler = new DefaultHttpErrorHandler(env, config, None/*sourceMapper*/, None/*router*/)
      implicit val webJarAssets: WebJarAssets = new WebJarAssets(errorHandler, config, env)

      val stocksActor = TestProbe("stocksActor")
      val userParentActor = TestProbe("userParentActor")
      val userActor = TestProbe("userActor")

      // http://doc.akka.io/docs/akka/2.4.4/scala/stream/stream-testkit.html
      val publisher = akka.stream.testkit.TestPublisher.probe[JsValue]()

      // instantiate the controller...
      val controller = new HomeController(stocksActor.ref, userParentActor.ref)

      // call method under test...
      val flowUnderTest = controller.createWebSocketFlow(publisher, userActor.ref)

      // create a test source and sink around the flow
      val (pub, sub) = TestSource.probe[JsValue]
        .via(flowUnderTest)
        .toMat(TestSink.probe[JsValue])(Keep.both)
        .run()

      val jsvalue = Json.obj("herp" -> "derp")

      // check that a message sent in will come out the other end
      sub.request(n = 1)
      publisher.sendNext(jsvalue)
      sub.expectNext(jsvalue)
    }

  }


}
