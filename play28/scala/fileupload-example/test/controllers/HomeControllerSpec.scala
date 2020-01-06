package controllers

import java.io._
import java.nio.file.Files

import akka.stream.scaladsl._
import akka.util.ByteString
import akka.util.Timeout
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.duration._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HomeControllerSpec extends PlaySpec with GuiceOneServerPerSuite with Injecting {

  "HomeController" must {
    "upload a file successfully" in {
      val tmpFile = java.io.File.createTempFile("prefix", "txt")
      tmpFile.deleteOnExit()
      val msg = "hello world"
      Files.write(tmpFile.toPath, msg.getBytes())

      val url = s"http://localhost:${port}/upload"
      val responseFuture = inject[WSClient].url(url).post(postSource(tmpFile))
      val response = await(responseFuture)(Timeout(10.seconds))
      response.status mustBe OK
      response.body mustBe "file size = 11"
    }
  }

  def postSource(tmpFile: File): Source[MultipartFormData.Part[Source[ByteString, _]], _] = {
    import play.api.mvc.MultipartFormData._
    Source(FilePart("name", "hello.txt", Option("text/plain"),
      FileIO.fromPath(tmpFile.toPath)) :: DataPart("key", "value") :: List())
  }
}
