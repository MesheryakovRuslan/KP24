//
//import ControllerActor.ChatEvent
//import akka.actor.typed.pubsub.Topic
//import akka.actor.typed.Behavior
//
//
//object MainActor{
//  def startActorSystem() = Behavior.setyp({
//    context =>
//      val controllerActor = context.spawn(ControllerActor(),"controllerActor")
//      val topic = context.spawn(Topic[ChatEvent]("topic"),"topic")
//
//      topic ! Topic.subscribe(controllerActor)
//  })
//}