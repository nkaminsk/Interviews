package persistence

import modules.DataLoader
import javax.inject.Inject

class DataDao @Inject()(
  val dataLoader: DataLoader
){

  def getById(id: String) = {
    dataLoader.getById(id)
  }

  def getByState(state: String) = {
    dataLoader.getByState(state)
  }

  def getByStatePaginated(state: String, page: Int, pageSize: Int) = {
    dataLoader.getByStatePaginated(state, page, pageSize)
  }

}
