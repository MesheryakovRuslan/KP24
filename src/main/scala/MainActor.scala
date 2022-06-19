import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors


object MainActor{
  final case class SayHello(name: String)

  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>
      val greeter = context.spawn(ControllerActor(), "greeter")

      Behaviors.receiveMessage { message =>
        val replyTo = context.spawn(HelloWorldBot(max = 3), message.name)
        greeter ! ControllerActor.Greet(message.name, replyTo)
        Behaviors.same
      }
    }
}