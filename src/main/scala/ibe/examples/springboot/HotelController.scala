package ibe.examples.springboot

import java.util.function.Consumer
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * See https://github.com/bijukunjummen/spring-boot-scala-web/
  */
@RestController
class HotelController {
  //noinspection VarCouldBeVal
  @Autowired var hotelRepository: HotelRepository = _
  //noinspection VarCouldBeVal
  @Autowired var helloWorld: HelloWorld = _

  @RequestMapping(Array("/"))
  def handleRootRequest(response: HttpServletResponse) = response.sendRedirect("/hotels")

  @RequestMapping(value = Array("/foo"), method = Array(RequestMethod.GET))
  def foo() = {
    helloWorld.foo
  }

  @RequestMapping(value = Array("/hotels"), method = Array(RequestMethod.GET))
  def list() = {
    hotelRepository.findAll()
  }

  //
  //    @RequestMapping(Array("/edit/{id}"))
  //    def edit(@PathVariable("id") id: Long, model: Model) = {
  //      model.addAttribute("hotel", hotelRepository.findOne(id))
  //      "hotels/edit"
  //    }
  //
  //    @RequestMapping(method = Array(RequestMethod.GET), params = Array("form"))
  //    def createForm(model: Model) = {
  //      model.addAttribute("hotel", new Hotel())
  //      "hotels/create"
  //    }
  //
  //    @RequestMapping(method = Array(RequestMethod.POST))
  //    def create(@Valid hotel: Hotel, bindingResult: BindingResult) = {
  //      if (bindingResult.hasErrors()) {
  //        "hotels/create"
  //      } else {
  //        hotelRepository.save(hotel)
  //        "redirect:/hotels"
  //      }
  //    }
  //
  //    @RequestMapping(value = Array("/update"), method = Array(RequestMethod.POST))
  //    def update(@Valid hotel: Hotel, bindingResult: BindingResult) = {
  //      if (bindingResult.hasErrors()) {
  //        "hotels/edit"
  //      } else {
  //        hotelRepository.save(hotel)
  //        "redirect:/hotels"
  //      }
  //    }
  //
  //    @RequestMapping(value = Array("/delete/{id}"))
  //    def delete(@PathVariable("id") id: Long) = {
  //      hotelRepository.delete(id)
  //      "redirect:/hotels"
  //    }
  //  }
}

class HotelList (hotels : List[Hotel]){}