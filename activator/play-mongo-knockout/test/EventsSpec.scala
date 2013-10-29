import models.Message
import org.specs2.mutable.Specification
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import services.{EventDao, MessageDao}
import MongoDBTestUtils.withMongoDb

object EventsSpec extends Specification {

  "Events" should {

    "publish a new event when a message is saved" in withMongoDb { implicit app =>
      val futureEvent = EventDao.stream |>>> Iteratee.head
      val message = Message(BSONObjectID.generate, "Foo")
      MessageDao.save(message)

      Await.result(futureEvent, Duration.Inf) must beSome.like {
        case event =>
          event.name must_== "message"
          Json.fromJson[Message](event.data).asOpt must beSome(message)
      }
    }
  }
}
