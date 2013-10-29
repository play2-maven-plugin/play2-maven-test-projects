import controllers.MessageController
import models.Message
import services.MessageDao

import org.specs2.mutable.Specification
import MongoDBTestUtils._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object MessageControllerSpec extends Specification {

  "the message controller" should {

    "save a message" in withMongoDb { implicit app =>
      status(MessageController.saveMessage(FakeRequest().withBody(Json.obj("message" -> "Foo")))) must_== CREATED
      val messages = Await.result(MessageDao.findAll(0, 10), Duration.Inf)
      messages must haveSize(1)
      messages.head.message must_== "Foo"
    }

    "get messages" in withMongoDb { implicit app =>
      createMessage("Foo")
      createMessage("Bar")
      val messages = Json.parse(contentAsString(MessageController.getMessages(0, 10)(FakeRequest()))).as[Seq[Message]]
      messages must haveSize(2)
      messages(0).message must_== "Bar"
      messages(1).message must_== "Foo"
    }

    "page messages" in withMongoDb { implicit app =>
      for (i <- 1 to 30) {
        createMessage("Message " + i)
      }
      def test(page: Int, perPage: Int) = {
        val result = MessageController.getMessages(page, perPage)(FakeRequest())
        val (prev, next) = header("Link", result).map { link =>
          (extractLink("prev", link), extractLink("next", link))
        }.getOrElse((None, None))
        (prev.isDefined, next.isDefined, Json.parse(contentAsString(result)).as[Seq[Message]].size)
      }

      test(0, 10) must_== (false, true, 10)
      test(1, 10) must_== (true, true, 10)
      test(2, 10) must_== (true, false, 10)
      test(3, 10) must_== (true, false, 0)
      test(0, 30) must_== (false, false, 30)
      test(0, 31) must_== (false, false, 30)
      test(0, 29) must_== (false, true, 29)
    }
  }

  def createMessage(msg: String) = {
    Await.result(MessageDao.save(Message(BSONObjectID.generate, msg)), Duration.Inf)
  }

  def extractLink(rel: String, link: String) = {
    """<([^>]*)>;\s*rel="%s"""".format(rel).r.findFirstMatchIn(link).map(_.group(1))
  }
}
