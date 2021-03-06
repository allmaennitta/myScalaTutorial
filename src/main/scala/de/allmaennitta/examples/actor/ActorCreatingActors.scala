package de.allmaennitta.examples.actor

import akka.actor.{Actor, Props}
object ActorCreatingActors{

  def props: Props = Props(new ActorCreatingActors())
}

class ActorCreatingActors extends Actor{
  def receive: Receive = {
    case _ =>
      val sender2 = context.actorOf(ColoredActor.props("red"), "sender2")
      sender2 ! "Hello, world!"
  }

}


