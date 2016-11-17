package ibe.examples.springboot

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.SmartLifecycle
import org.springframework.jmx.export.MBeanExporter

import scala.beans.BeanProperty


class AService (@Autowired() helloWorld: HelloWorld) {
  @PostConstruct
  def initialize() {
    println (helloWorld.foo)
  }
}
