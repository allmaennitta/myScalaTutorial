package ibe.examples.springboot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration}

object SpringBoot extends App {
  val ctx = SpringApplication.run(classOf[Config])

  def foo = ctx.getBean("helloWorld").asInstanceOf[HelloWorld].foo

}

@Configuration
@EnableAutoConfiguration
class Config(){
  @Bean
  def helloWorld = HelloWorld()

}
