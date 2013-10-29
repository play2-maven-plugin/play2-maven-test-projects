package services

import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.json.BSONFormats._
import play.api.Play.current
import models._
import models.Message._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import reactivemongo.api.QueryOpts
import reactivemongo.core.commands.Count
import reactivemongo.bson.BSONObjectID

/** A data access object for messages backed by a MongoDB collection */
object MessageDao {

  /** The messages collection */
  private def collection = ReactiveMongoPlugin.db.collection[JSONCollection]("messages")

  /**
   * Save a message.
   *
   * @return The saved message, once saved.
   */
  def save(message: Message): Future[Message] = {
    collection.save(message).map {
      case ok if ok.ok =>
        EventDao.publish("message", message)
        message
      case error => throw new RuntimeException(error.message)
    }
  }

  /**
   * Find all the messages.
   *
   * @param page The page to retrieve, 0 based.
   * @param perPage The number of results per page.
   * @return All of the messages.
   */
  def findAll(page: Int, perPage: Int): Future[Seq[Message]] = {
    collection.find(Json.obj())
      .options(QueryOpts(skipN = page * perPage))
      .sort(Json.obj("_id" -> -1))
      .cursor[Message]
      .toList(perPage)
  }

  /** The total number of messages */
  def count: Future[Int] = {
    ReactiveMongoPlugin.db.command(Count(collection.name))
  }

}
