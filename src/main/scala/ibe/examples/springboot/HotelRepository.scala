package ibe.examples.springboot

import org.springframework.data.repository.CrudRepository
import java.lang.Long

/**
  * See https://github.com/bijukunjummen/spring-boot-scala-web/
  */
trait HotelRepository extends CrudRepository[Hotel, Long]