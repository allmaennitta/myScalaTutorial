package ibe.examples.actor

import akka.actor.{Actor, ActorSystem, Props}

object ColoredActor{
  def props(preferedColor: String): Props = Props(new ColoredActor(preferedColor))
}

class ColoredActor(preferedColor: String) extends Actor {

  var lastMsg: String = ""
  var preferredLanguage: String = preferedColor

  def receive: Receive = {
    case msg: String => {
      lastMsg = msg
      //var is just necessary for test, variable could be also passed directly from signature
      println(s"By the way, did you know my preferred color is ${preferedColor} ?")
    }
  }
}