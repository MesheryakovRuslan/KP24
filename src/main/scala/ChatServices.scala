import java.io.{File, FileWriter}
import scala.util.Using

class ChatServices(val login: String) {
  val PATH_TO_CHAT = s"src/main/resources/Chats/$login"
  val PATH_TO_ROOM = s"src/main/resources/Room/$login"
  val PUBLIC_CHAT = "room"
  val PRIVATE_CHAT = "chat"
  var activeChatPath = ""
  resolveDir(PATH_TO_CHAT)
  resolveDir(PATH_TO_ROOM)

  def resolveDir(path:String): Unit ={
    val theDir = new File(path)
    if (!theDir.exists) theDir.mkdirs
  }

  def createRoom(): Boolean ={
    val pathToFile = PATH_TO_ROOM+ "/MainRoom.txt"
    val fileObject = new File(pathToFile)
    val flag = fileObject.createNewFile()
    print(fileObject.getPath)
    flag
  }

  def createFile(nameFile:String): Boolean ={
    val pathToFile = PATH_TO_CHAT + "/" + nameFile + ".txt"
    val fileObject = new File(pathToFile)
    val flag = fileObject.createNewFile()
    print(fileObject.getPath)
    flag
  }
  def writeMessageToFile (nameFile:String, message:String,directory:String): Unit ={
    if (directory == PRIVATE_CHAT) {
      Using(new FileWriter( PATH_TO_CHAT + "/" + nameFile + ".txt", true)) { fileWriter =>
      fileWriter.write(message + "\n")
      fileWriter.flush()
    }
    }else {
      Using(new FileWriter(PATH_TO_ROOM + "/"  + nameFile + ".txt",true)) { fileWriter =>
        fileWriter.write(message + "\n")
        fileWriter.flush()
      }
    }
  }

  def loadFile(path:String): Array [File] ={
    val fileFriend = new File(path)
    val arrFriend = fileFriend.listFiles
    arrFriend
  }

  def getActivePath(): String = {
    activeChatPath
  }

  def setActiveChat(name:String): Unit = {
    activeChatPath = PATH_TO_CHAT + "/"  + name +".txt"
  }

  def setActiveRoom(name:String): Unit = {
    activeChatPath = PATH_TO_ROOM + "/"  + name + ".txt"
  }

}
