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
  val filePath = "/data/dataset2.csv"
  val inputStream = this.getClass.getResourceAsStream(filePath)
  val reader = CSVReader.open(new InputStreamReader(inputStream))

  val allData = reader.allWithHeaders()

  def getData() = allData

}
