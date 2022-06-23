import akka.actor.TypedActor.context
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object ControllerActor {
//  trait RoomCommand
//  final case class getSession (userName : String, replyTo:ActorRef[ChatEvent]) extends RoomCommand

  trait ChatEvent
  final case class sendMessage (textMessage:String,recipientName:String) extends ChatEvent
  final case class receiveMessages (textMessage:String,senderName:String) extends ChatEvent
//  final case class friendRequest (friendName:String) extends ChatEvent
//  final case class replyToFriendRequest (friendName:String, reply: Boolean) extends ChatEvent
//  final case class sendMessageToConversation ()
//  final case class receiveMessageToConversation ()

  def apply():Behavior[ChatEvent]
  Behaviors.setup(context => new ControllerActorBehavior(context))

  def controllerActor(sessions: List[ActorRef[ChatEvent]]):Behavior[ChatEvent] =
    Behaviors.receive{(context,message) =>
      message match {
        case sendMessage(textMessage,recipientName) =>


      }
    }
  }

