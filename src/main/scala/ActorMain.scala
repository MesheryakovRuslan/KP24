import ControllerActor.ChatEvent
import akka.actor.typed.Behavior
import akka.actor.typed.pubsub.Topic
import akka.actor.typed.scaladsl.Behaviors

object ActorMain {
  def apply(chatMainControllerLogic: ChatMainControllerLogic): Behavior[ChatEvent] =
    Behaviors.setup { context =>
      val userActor = context.spawn(ControllerActor(chatMainControllerLogic), "greeter")
      val topic = context.spawn(Topic[ChatEvent]("my-topic"), "MyTopic")
      topic ! Topic.Subscribe(userActor)

      Behaviors.receiveMessage { message =>
        topic ! Topic.Publish(message)
        Behaviors.same
      }
    }
}
