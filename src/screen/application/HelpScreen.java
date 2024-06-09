package screen.application;

import screen.controller.HelpController;

import board.Board;
import player.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelpScreen extends Application{
    private static Board board;
    private static Player player1, player2;
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader((HelpScreen.class.getResource("resource/HelpScreen.fxml")));
        HelpController helpScreenController = new HelpController(board, player1, player2);
        fxmlLoader.setController(helpScreenController);
        Parent root = null;
        try {
            root = fxmlLoader.load();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Image icon = new Image("screen/images/icon.jpg");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("O an quan");
        stage.show();
    }
    public static void main(String[] args) {
        board = new Board();
        player1 = new Player();
        player2 = new Player();

        launch(args);
    }
}
