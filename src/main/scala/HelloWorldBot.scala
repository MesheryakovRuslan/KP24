import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{Behaviors, LoggerOps}

object HelloWorldBot {

    def apply(max: Int): Behavior[ControllerActor.Greeted] = {
      bot(0, max)
    }

    private def bot(greetingCounter: Int, max: Int): Behavior[ControllerActor.Greeted] =
      Behaviors.receive { (context, message) =>
        val n = greetingCounter + 1
        context.log.info2("Greeting {} for {}", n, message.whom)
        if (n == max) {
          Behaviors.stopped
        } else {
          message.from ! ControllerActor.Greet(message.whom, context.self)
          bot(n, max)
        }
      }
  }

