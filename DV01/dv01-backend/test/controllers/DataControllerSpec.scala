package controllers

import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsString, defaultAwaitTimeout, status, stubControllerComponents}
import services.DataService

class DataControllerSpec extends AnyFreeSpec with Matchers with MockitoSugar {

  trait DataControllerTest {
    val mockDataService = mock[DataService]
    val controller = new DataController(stubControllerComponents(), mockDataService)

    val id = "126378396"
    val badId = "42"

    when(mockDataService.getById(anyString())) thenReturn List(Map("id" -> "126378396"))
  }

  "DataController GET" - {
    "GetById" in new DataControllerTest {
      val req = controller.getById(anyString()).apply(FakeRequest(GET, s"/getById/$id"))

      contentAsString(req) mustBe s"Returned Data: [Map(id -> 126378396)]"
      status(req) mustBe OK
    }
    "GetById bad data" in new DataControllerTest {
      when(mockDataService.getById(anyString())) thenReturn List.empty

      val req = controller.getById(anyString()).apply(FakeRequest(GET, s"/getById/$badId"))

      contentAsString(req) mustBe s"Not Found"
      status(req) mustBe NOT_FOUND
    }
  }
}
