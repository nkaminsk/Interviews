package persistence

import modules.DataLoader
import javax.inject.Inject

class DataDao @Inject()(
  val dataLoader: DataLoader
){

  def getById(id: String) = {
    dataLoader.getById(id)
  }
}
