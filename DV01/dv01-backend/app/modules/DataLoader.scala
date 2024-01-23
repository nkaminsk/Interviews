package modules

import com.github.tototoshi.csv.CSVReader
import com.google.inject.AbstractModule

import java.io.InputStreamReader

/**
This module is for importing and storing CSV data on application start up
 */

class DataLoaderModule extends AbstractModule {
  override def configure() = {
    bind(classOf[DataLoader]).asEagerSingleton()
  }
}

class DataLoader {

  // Starting code for importing CSV data
  val filePath = "/data/dataset.csv"
  val inputStream = this.getClass.getResourceAsStream(filePath)
  val reader = CSVReader.open(new InputStreamReader(inputStream))

  val allData = reader.allWithHeaders()

  // Create maps for immediate data access
  val indexedById: Map[String, List[Map[String, String]]] = allData.groupBy(row => row("id"))
  val indexedByState: Map[String, List[Map[String, String]]] = allData.groupBy(row => row("addr_state"))


  //Access functions

  def getData() = allData

  def getById(id: String): List[Map[String, String]] = indexedById.getOrElse(id, List.empty)

  def getByState(state: String): List[Map[String, String]] = indexedByState.getOrElse(state, List.empty)

  def getByStatePaginated(state: String, page: Int, pageSize: Int): List[Map[String, String]] = {
    val startIndex = (page - 1) * pageSize
    indexedByState.getOrElse(state, List.empty)
      .slice(startIndex, startIndex + pageSize)
  }
}
