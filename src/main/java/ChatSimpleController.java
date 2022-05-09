import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatSimpleController implements Initializable {

    @FXML
    protected GridPane MainWindow;

    @FXML
    protected HBox HBoxMessageControl;

    @FXML
    protected TextField ChatTFMessage;

    @FXML
    protected Button ChatBTNSend;

    @FXML
    protected VBox VBoxChatMessage;

    @FXML
    protected VBox VBoxFriendList;

    @FXML
    protected Label ChatWidthFriendLabel;

    @FXML
    protected HBox HBoxPanelForOption;

    @FXML
    protected Button AddFriendBTN;

    @FXML
    protected Button ConversationBTN;

    @FXML
    protected HBox HBoxTitleChat;

    @FXML
    protected Label ChatListLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
