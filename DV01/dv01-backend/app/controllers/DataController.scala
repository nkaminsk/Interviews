package controllers

import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}
import services.DataService

import javax.inject.{Inject, Singleton}

/**
 * This controller creates an `Action` to handle HTTP requests
 * relating to handling data
 */

@Singleton
class DataController @Inject()(
  val controllerComponents: ControllerComponents,
  val dataService: DataService
) extends BaseController {

  def getById(id: String) = Action { implicit request: Request[AnyContent] =>
    val data = dataService.getById(id)
    if (data.nonEmpty)
      Ok(s"Returned Data: [${data.mkString(", ")}]")
    else
      NotFound(s"Not Found")
  }

  def getByState(state: String) = Action { implicit request: Request[AnyContent] =>
    val data = dataService.getByState(state)
    if (data.nonEmpty)
      Ok(s"Returned Data: [${data.mkString(", ")}]")
    else
      NotFound(s"Not Found")
  }

  def getByStatePaginated(state: String, page: Int, pageSize: Int) = Action { implicit request: Request[AnyContent] =>
    val data = dataService.getByStatePaginated(state, page, pageSize)
    if (data.nonEmpty)
      Ok(s"Returned Data: [${data.mkString(", ")}]")
    else
      NotFound(s"Not Found")
  }
}
