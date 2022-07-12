import ControllerActor._
import akka.actor.typed.ActorSystem
import akka.cluster.typed
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.geometry.Insets
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Label}
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import java.io._
import java.net.URL
import java.util.ResourceBundle
import scala.io.Source

object ChatMainControllerLogic{
  def initializeFrame(login:String): Unit ={
    val loader = new FXMLLoader(getClass.getResource("FXML/ChatSemple.fxml"))
    val root:Parent = loader.load()
    val controller:ChatMainControllerLogic = loader.getController
    val stage = new Stage()

    controller.login = login
    stage.setTitle("Chat: " + login)
    stage.setScene(new Scene(root))
    stage.setResizable(false)
    stage.show()

    controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController")
    val clusterSystem = typed.Cluster(controller.actorSystem)

  }
}

class ChatMainControllerLogic extends ChatMainController {
  var activeChatPath = ""
  var chatType = ""
  var activeChatUser = ""
  var login = ""
  var actorSystem: ActorSystem[ControllerActor.ChatEvent] = _

  actorSystem ! UserOnline(login)

    override def actionChatBTNSend(Event: ActionEvent): Unit = {
      if (ChatTFMessage.getText.nonEmpty && chatType.trim.nonEmpty ) {
        printUserMessage()
        ChatScrollPane.setVvalue(1)
        ChatTFMessage.setText("")
      }
    }

    override def actionAddFriendBTN(Event: ActionEvent): Unit = {
      val PathToFile = "src/main/resources/Chats/" + NameAddFriendTF.getText + ".txt"
      val fileObject = new File(PathToFile)
      val flag = fileObject.createNewFile()

      if (flag) {
        addFriendIntoFriendList()
        print(fileObject.getPath)
        val printWriter = new PrintWriter(fileObject)
        printWriter.close()
        NameAddFriendTF.setText("")
        ChatUIDTF.setText("")
      } else {
        val alert = new Alert(AlertType.WARNING)
        alert.setTitle("error")
        alert.setContentText("friend add failed")
        alert.show()
      }
    }

    override def actionConversationBTN(Event: ActionEvent): Unit = {
    }

    def printReceivedMessage(textMessage:String, receiveName:String): Unit = {
      if (receiveName==activeChatUser) {
        val label = new Label
        label.setFont(new Font("Arial", 18.0))
        label.setTextFill(Color.web("#D33CB5"))
        label.setText(textMessage)
        label.setWrapText(true)
        label.setPadding(new Insets(0, 0, 10, 0))
        VBoxChatMessage.getChildren.addAll(label)
      }

      if(chatType == "chat") {
        val fileWriter = new FileWriter("src/main/resources/Chats/"+ receiveName.trim + ".txt",true)
        fileWriter.write(textMessage + "\n")
        fileWriter.close()
        VBoxChatMessage.getChildren.clear()
        loadChat(activeChatPath)
      } else {
        val fileWriter = new FileWriter("src/main/resources/Room/"+ receiveName.trim + ".txt",true)
        fileWriter.write(textMessage + "\n")
        fileWriter.close()
        VBoxChatMessage.getChildren.clear()
        loadChat(activeChatPath)
      }
    }

    def printUserMessage(): Unit = {
      if(ChatTFMessage.getText.nonEmpty) {
        val message = login + " => " + ChatTFMessage.getText
        val label = new Label
        val fileWriter = new FileWriter(activeChatPath, true)
        fileWriter.write(message + "\n")
        fileWriter.close()
        label.setFont(new Font("Arial", 18.0))
        label.setTextFill(Color.web("#0076a3"))
        label.setText(message)
        label.setWrapText(true)
        label.setPadding(new Insets(0, 0, 10, 0))
        VBoxChatMessage.getChildren.addAll(label)
        actorSystem ! SendMessage(message, activeChatUser, login)
      }
    }

    override def actionVueFriendBTN(Event: ActionEvent): Unit = {
      if (AddFriendPanel.isVisible) {
        AddFriendPanel.setVisible(false)
        FriendListScrollPane.setPrefHeight(470)
      } else {
        AddFriendPanel.setVisible(true)
      }
    }

    def addFriendIntoFriendList(): Unit = {
      val nameFriend = NameAddFriendTF.getText
      val UIDFriend = ChatUIDTF.getText
      val label = new Label

      label.setText(nameFriend)
      label.setPrefWidth(110.0)
      label.setPrefHeight(39.0)
      label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
      label.setOnMouseClicked(ActionEvent => {
        VBoxChatMessage.getChildren.clear()
        activeChatPath = "src/main/resources/Chats/" + label.getText + ".txt"
        UserNameLabel.setText(label.getText)
        chatType = "chat"
        loadChat(activeChatPath)
        activeChatUser = label.getText.trim
      })
      FriendListVbox.getChildren.add(label)
    }

    override def initialize(location: URL, resources: ResourceBundle): Unit = {
      AddFriendPanel.setVisible(false)
      ChatScrollPane.setFitToWidth(true)
      loadChatPanel()
    }

    def loadChatPanel(): Unit ={
      loadRoom()
      loadStoryChat()
      //loadOnlineUser(userName)
    }

    def loadOnlineUser(userName: String): Unit ={
      //Загрузка пользователей онлайн
      val titleOnlineUserBorder = new Label
      titleOnlineUserBorder.setText("Online")
      FriendListVbox.getChildren.add(titleOnlineUserBorder)
      val label = new Label
      label.setText(userName)
      label.setPrefWidth(130.0)
      label.setPrefHeight(39.0)
      label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
      label.setOnMouseClicked(ActionEvent =>{
        val PathToFile = "src/main/resources/Chats/" + userName + ".txt"
        val fileObject = new File(PathToFile)
        //val flag = fileObject.createNewFile()
        VBoxChatMessage.getChildren.clear()
        activeChatPath = "src/main/resources/Chats/"+ label.getText+".txt"
        UserNameLabel.setText(label.getText)
        chatType = "chat"
        loadChat(activeChatPath)
        activeChatUser = label.getText.trim
      })
      UserNameLabel.setText("")
      FriendListVbox.getChildren.add(label)
    }

    def loadStoryChat(): Unit ={
      //Загрузка чатов(Приватных)
      val fileFriend = new File("src/main/resources/Chats")
      val arrFriend = fileFriend.listFiles
      val titleFriendBorder = new Label
      titleFriendBorder.setText("Friend chat")
      FriendListVbox.getChildren.add(titleFriendBorder)
      arrFriend.foreach{f:File =>
        val label = new Label
        label.setText(f.getName.substring(0,f.getName.length-4))
        label.setPrefWidth(130.0)
        label.setPrefHeight(39.0)
        label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
        label.setOnMouseClicked(ActionEvent =>{
          VBoxChatMessage.getChildren.clear()
          activeChatPath = "src/main/resources/Chats/"+ label.getText+".txt"
          UserNameLabel.setText(label.getText)
          chatType = "chat"
          loadChat(activeChatPath)
          activeChatUser = label.getText.trim
        })
        UserNameLabel.setText("")
        FriendListVbox.getChildren.add(label)
      }
    }

    def loadRoom(): Unit ={
      //Загрузка чатов(Публичных)
      val titleRoomBorder = new Label
      titleRoomBorder.setText("Room chat")
      FriendListVbox.getChildren.add(titleRoomBorder)
      val fileRoom = new File("src/main/resources/Room")
      val arrRoom = fileRoom.listFiles
      arrRoom.foreach{f:File =>
        val label = new Label
        label.setText(f.getName.substring(0,f.getName.length-4))
        label.setPrefWidth(130.0)
        label.setPrefHeight(39.0)
        label.setStyle("-fx-background-color: #010f1a; -fx-border-color: #949049;")
        label.setOnMouseClicked(ActionEvent =>{
          VBoxChatMessage.getChildren.clear()
          activeChatPath = "src/main/resources/Room/"+ label.getText+".txt"
          chatType="room"
          UserNameLabel.setText(label.getText)
          loadChat(activeChatPath)
          activeChatUser = label.getText.trim
        })
        UserNameLabel.setText("")
        FriendListVbox.getChildren.add(label)
      }
    }

    def  loadChat(path: String) {
      val lines = Source.fromFile(path).getLines.toList
      lines.foreach{f:String=>
        val label = new Label
        label.setFont(new Font("Arial", 18.0))
        label.setTextFill(Color.web("#0076a3"))
        val fixed = new String(f.getBytes())
        label.setText(fixed)
        label.setWrapText(true)
        label.setPadding(new Insets(0, 0, 10, 0))
        VBoxChatMessage.getChildren.addAll(label)
      }
    }
}

