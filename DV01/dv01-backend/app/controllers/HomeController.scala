package controllers

import com.github.tototoshi.csv.CSVReader
import play.api.mvc._

import java.io.InputStreamReader
import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
  val controllerComponents: ControllerComponents,
//  val dataLoader: DataLoader
) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  private final val filePath = "/data/dataset.csv"
  def index() = Action { implicit request: Request[AnyContent] =>
//    val allData = dataLoader.getData()
    Ok(s"Welcome to Play")
  }
}
