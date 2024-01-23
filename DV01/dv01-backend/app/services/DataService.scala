package services

import persistence.DataDao

import javax.inject.Inject

class DataService @Inject()(
  val dataDao: DataDao
){
  def getById(id: String): List[Map[String, String]] = {
    dataDao.getById(id)
  }
}
