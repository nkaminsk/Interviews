package services

import persistence.DataDao

import javax.inject.Inject

class DataService @Inject()(
  val dataDao: DataDao
){
  def getById(id: String): List[Map[String, String]] = {
    dataDao.getById(id)
  }

  def getByState(state: String): List[Map[String, String]] = {
    dataDao.getByState(state)
  }

  def getByStatePaginated(state: String, page: Int, pageSize: Int): List[Map[String, String]] = {
    dataDao.getByStatePaginated(state, page, pageSize)
  }
}
