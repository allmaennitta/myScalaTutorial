package ibe.examples.actor

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{FunSpecLike, Matchers}


class ActorTest() extends TestKit(ActorSystem("testSystem"))
  with Matchers
  with FunSpecLike{

  describe("An Actor ") {
    it("can receive a String message") {
      val simpleActor = TestActorRef[SimpleActor]
      simpleActor ! "hello"

      simpleActor.underlyingActor.lastMsg should be("hello")
    }

    it("can be initialized with a color") {
      //val coloredActor = TestActorRef[new ColoredActor("green")]
//      val coloredActor = TestActorRef[new MyActor]
//      coloredActor ! "moin"
//
//      coloredActor.underlyingActor.lastMsg should be("moin")
//      coloredActor.underlyingActor.preferredLanguage should be ("green")
//
//      coloredActor.underlyingActor.lastMsg should be("moin")
//      coloredActor.underlyingActor.lastMsg should be("green")
    }


//      val system = ActorSystem("myFirstAccaTest")
//      val simpleActor = system.actorOf(Props(new SimpleActor), "simpleActor")
//      val sender = system.actorOf(ColoredActor.props("blue"), "sender")
//      val aca = system.actorOf(ActorCreatingActors.props, "aca")
//      sender ! "Hello, world!"
//      reader ! "Hello, world!"
//      aca ! "Whazzzuuuup?!"
//      Thread sleep 10
//
//      MessageBox.toString should be ("Hello, world!")
    }

  }
