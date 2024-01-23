package controllers

import org.mockito.ArgumentMatchers.{anyInt, anyString}
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
    val state = "CA"
    val badState = "liquid"

    when(mockDataService.getById(anyString())) thenReturn List(Map("id" -> "126378396"))
    when(mockDataService.getByState(anyString())) thenReturn List(Map("state" -> "CA"))
    when(mockDataService.getByStatePaginated(anyString(), anyInt(), anyInt())) thenReturn List(Map("state" -> "CA"))
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

    "GetByState" in new DataControllerTest {
      val req = controller.getByState(anyString()).apply(FakeRequest(GET, s"/getById/$id"))

      contentAsString(req) mustBe s"Returned Data: [Map(state -> CA)]"
      status(req) mustBe OK
    }

    "GetByState bad data" in new DataControllerTest {
      when(mockDataService.getByState(anyString())) thenReturn List.empty

      val req = controller.getByState(anyString()).apply(FakeRequest(GET, s"/getById/$badState"))

      contentAsString(req) mustBe s"Not Found"
      status(req) mustBe NOT_FOUND
    }
  }

  "GetByStatePaginated" in new DataControllerTest {
    val req = controller.getByStatePaginated(anyString(), anyInt(), anyInt()).apply(FakeRequest(GET, s"/getById/$id"))

    contentAsString(req) mustBe s"Returned Data: [Map(state -> CA)]"
    status(req) mustBe OK
  }

  "GetByStatePaginated bad data" in new DataControllerTest {
    when(mockDataService.getByStatePaginated(anyString(), anyInt(), anyInt())) thenReturn List.empty

    val req = controller.getByState(anyString()).apply(FakeRequest(GET, s"/getById/$badState"))

    contentAsString(req) mustBe s"Not Found"
    status(req) mustBe NOT_FOUND
  }
}
