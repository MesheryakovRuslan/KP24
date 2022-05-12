import javafx.event.ActionEvent

class ChatLoginLogicController extends ChatLoginController {

   override def actionLoginBTN(event: ActionEvent): Unit = {
     println("BTN click")
    if (NameTF.getText.nonEmpty & PasswordTF.getText.nonEmpty) {
      println("Ok name:" + NameTF.getText + " password: " + PasswordTF.getText)
    } else {
      println("Empty")
    }
  }
}
