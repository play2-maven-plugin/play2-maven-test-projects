package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._

import views._

class Application @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

  /**
   * Describes the hello form.
   */
  val helloForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "repeat" -> number(min = 1, max = 100),
      "color" -> optional(text)
    )
  )

  // -- Actions

  /**
   * Home page
   */
  def index = Action {
    Ok(html.index(helloForm))
  }

  /**
   * Handles the form submission.
   */
  def sayHello = Action { implicit request =>
    helloForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.index(formWithErrors)),
      {case (name, repeat, color) => Ok(html.hello(name, repeat.toInt, color))}
    )
  }

}
