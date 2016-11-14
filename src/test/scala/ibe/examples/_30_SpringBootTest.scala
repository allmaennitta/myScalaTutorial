package ibe.examples

import ibe.examples.springboot.SpringBoot
import org.scalatest.{FunSpec, Matchers}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.IntegrationTest
import org.springframework.context.annotation.{ComponentScan, Configuration}

class _30_SpringBootTest extends FunSpec with Matchers {

  /**
    * Please note files
    *   src/main/resources/
    *     application.properties
    *     logback.xml
    *   project/plugins.sbt (for building project per sbt)
    *   build.sbt
    */
 describe("SpringBoot") {
   it("can be started and initialized") {
      SpringBoot.main(Array())
      SpringBoot.foo should be ("bar")
    }
  }
}


