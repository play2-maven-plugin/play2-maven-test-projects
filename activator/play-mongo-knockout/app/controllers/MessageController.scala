package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits._
import models._
import services.MessageDao
import reactivemongo.bson.BSONObjectID

object MessageController extends Controller {

  /** Action to get the messages */
  def getMessages(page: Int, perPage: Int) = Action { implicit req =>
    Async {
      for {
        count <- MessageDao.count
        messages <- MessageDao.findAll(page, perPage)
      } yield {
        val result = Ok(Json.toJson(messages))

        // Calculate paging headers, if necessary
        val next = if (count > (page + 1) * perPage) Some("next" -> (page + 1)) else None
        val prev = if (page > 0) Some("prev" -> (page - 1)) else None
        val links = next ++ prev
        if (links.isEmpty) {
          result
        } else {
          result.withHeaders("Link" -> links.map {
            case (rel, p) =>
              "<" + routes.MessageController.getMessages(p, perPage).absoluteURL() + ">; rel=\"" + rel + "\""
          }.mkString(", "))
        }
      }
    }
  }

  /**
   * The message form.  This is separate from the database message since the form doesn't have an ID.
   */
  case class MessageForm(message: String) {
    def toMessage: Message = Message(BSONObjectID.generate, message)
  }

  implicit val messageFormFormat = Json.format[MessageForm]

  /** Action to save a message */
  def saveMessage = Action(parse.json) { req =>
    Json.fromJson[MessageForm](req.body).fold(
      invalid => BadRequest("Bad message form"),
      form => Async {
        MessageDao.save(form.toMessage).map(_ => Created)
      }
    )
  }

}