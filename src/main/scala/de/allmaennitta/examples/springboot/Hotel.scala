package de.allmaennitta.examples.springboot

import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import javax.persistence.Entity

import com.fasterxml.jackson.annotation.JsonIgnore

import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty

/**
  * See https://github.com/bijukunjummen/spring-boot-scala-web/
  */
@Entity
class Hotel {

  @Id
  @GeneratedValue
  @BeanProperty //creates getter and setter
  var id: Long = _

  @BeanProperty
  @NotEmpty
  var name: String = _

  @BeanProperty
  @NotEmpty
  var address: String = _

  @BeanProperty
  @NotEmpty
  var zip: String = _

  @JsonIgnore
  var secret: String = _
}