import play.api._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.ReactiveMongoPlugin
import reactivemongo.api.DefaultDB
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Test utils for running tests with MongoDB
 */
object MongoDBTestUtils {

  /**
   * Run the given block with MongoDB
   */
  def withMongoDb[T](block: Application => T): T = {
    implicit val app = FakeApplication(
      additionalConfiguration = Map("mongodb.uri" -> "mongodb://localhost/unittests")
    )
    running(app) {
      val db = ReactiveMongoPlugin.db
      try {
        block(app)
      } finally {
        dropAll(db)
      }
    }
  }

  def dropAll(db: DefaultDB) = {
    Await.ready(Future.sequence(Seq(
      db.collection[JSONCollection]("messages").drop(),
      db.collection[JSONCollection]("events").drop()
    )), 2 seconds)
  }
}
