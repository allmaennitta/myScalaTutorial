package ibe.examples.actor

import akka.actor.Actor

class SimpleActor extends Actor {
var lastMsg: String = ""

  def receive = {

    case msg: String => {
      // Storing the message in the internal state variable
      lastMsg = msg
      sender ! "I received " + msg
    }
    case _ => println("sorry, what?")

    }
}