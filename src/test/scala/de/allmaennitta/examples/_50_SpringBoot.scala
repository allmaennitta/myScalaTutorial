package de.allmaennitta.examples

import com.jayway.jsonpath.JsonPath
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestContextManager

  /**
    * Please note files
    *   src/main/resources/
    *     application.properties
    *     logback.xml
    *   project/plugins.sbt (for building project per sbt)
    *   build.sbt
    */

  //@RunWith(classOf[SpringRunner])
  @SpringBootTest(classes = Array(classOf[de.allmaennitta.examples.springboot.MyConfig]), webEnvironment = WebEnvironment.RANDOM_PORT)
  class _50_SpringBoot extends FunSpec with Matchers with BeforeAndAfter  {

    //noinspection VarCouldBeVal
    @Autowired var restTemplate : TestRestTemplate = _

    before {
            val clz = this.getClass
            val tcm = new TestContextManager(clz)
            println(s"This: $this ; Class: $clz ; Context Manager: $tcm")
           tcm.prepareTestInstance(this)
    }

    describe("SpringBoot") {
      it("foo-method delivers expected bar") {
        this.restTemplate.getForObject("/foo", classOf[String]) should be ("bar")
      }
      it("can read hotels from db") {
        val jsonContext = JsonPath.parse(this.restTemplate.getForObject("/hotels", classOf[String]))
        jsonContext.read("$.[2].name").toString should be ("Test Hotel 3")
      }
    }
  }


