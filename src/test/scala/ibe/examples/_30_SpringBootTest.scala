package ibe.examples

import com.jayway.jsonpath.JsonPath
import ibe.examples.springboot.Hotel
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSpec, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.{ContextConfiguration, TestContextManager}
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.web.client.TestRestTemplate
  /**
    * Please note files
    *   src/main/resources/
    *     application.properties
    *     logback.xml
    *   project/plugins.sbt (for building project per sbt)
    *   build.sbt
    */

  @RunWith(classOf[SpringRunner])
  @SpringBootTest(classes = Array(classOf[ibe.examples.springboot.MyConfig]), webEnvironment = WebEnvironment.RANDOM_PORT)
  class _30_SpringBootTest extends FunSpec with Matchers  {
    @Autowired var restTemplate : TestRestTemplate = _

    describe("SpringBoot") {
     new TestContextManager(this.getClass).prepareTestInstance(this)

      it("foo-method delivers expected bar") {
        this.restTemplate.getForObject("/foo", classOf[String]) should be ("bar")
      }
      it("can read hotels from db") {
        val jsonContext = JsonPath.parse(this.restTemplate.getForObject("/hotels", classOf[String]))
        jsonContext.read("$.[2].name").toString should be ("Test Hotel 3")
      }
    }
  }


