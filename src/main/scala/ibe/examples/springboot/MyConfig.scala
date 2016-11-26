package ibe.examples.springboot

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(Array("ibe.examples.springboot")) //=> HotelRepository
class MyConfig(){
  @Bean
  def helloWorld = HelloWorld()

  @Bean
  def hotelController = new HotelController
}
