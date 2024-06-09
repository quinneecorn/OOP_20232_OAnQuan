package screen.controller;

import controller.MainScreenController;
import board.cells.*;


import board.Game;
import board.*;
import player.Player;
import board.stones.SmallStone;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

public class PlayScreenController implements Initializable {

    public Game game;
    public Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isP1Turn;
    private boolean isWaitMove;
    private Pane markedPane; // To mark which pane player pick up stones to move
    private final static double DURATION_TIME = 0.5;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;


    @FXML
    private Pane cell00, cell01, cell02, cell03, cell04;

    @FXML
    private Pane cell06, cell07, cell08, cell09, cell10;
    @FXML
    private Pane bigcell05, bigcell11;

    @FXML
    private Button leftButtonCell00, leftButtonCell01, leftButtonCell02, leftButtonCell03, leftButtonCell04;

    @FXML
    private Button leftButtonCell06, leftButtonCell07, leftButtonCell08, leftButtonCell09, leftButtonCell10;

    @FXML
    private ImageView leftDirectionCell00, leftDirectionCell01, leftDirectionCell02, leftDirectionCell03, leftDirectionCell04;

    @FXML
    private ImageView leftDirectionCell06, leftDirectionCell07, leftDirectionCell08, leftDirectionCell09, leftDirectionCell10;

    @FXML
    private Button mainScreen;

    @FXML
    private Button rightButtonCell00, rightButtonCell01, rightButtonCell02, rightButtonCell03, rightButtonCell04;

    @FXML
    private Button rightButtonCell06, rightButtonCell07, rightButtonCell08, rightButtonCell09, rightButtonCell10;

    @FXML
    private ImageView rightDirectionCell00, rightDirectionCell01, rightDirectionCell02, rightDirectionCell03, rightDirectionCell04;

    @FXML
    private ImageView rightDirectionCell06, rightDirectionCell07, rightDirectionCell08, rightDirectionCell09, rightDirectionCell10;

    @FXML
    private Label Player1Score, Player2Score;

    @FXML
    private Label CellNum00, CellNum01, CellNum02, CellNum03, CellNum04, CellNum06, CellNum07, CellNum08, CellNum09, CellNum10;

    @FXML
    private Label bigcellNum05, bigcellNum11;
    @FXML
    private Pane Player1bag, Player2bag;

    @FXML
    private ImageView big01, big02;

    @FXML
    private Media media;

    @FXML
    private MediaPlayer mediaPlayer = null;

    @FXML
    private Button adjustMusicButton;

    private boolean playMusic = false;

    @FXML
    private ImageView Mute;

    @FXML
    private ImageView unMute;


    public PlayScreenController(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    @FXML
    public void switchtoMainWindow(ActionEvent event) {
        this.mediaPlayer.stop();
        resetPlayScreen();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("screen/resource/MainScreen.fxml"));
            fxmlLoader.setController(new MainScreenController(board, player1, player2));
            root = fxmlLoader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isP1Turn = true;
        isWaitMove = true;
        for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04)) {
            pane.setDisable(false);
            pane.setCursor(Cursor.OPEN_HAND);
        }
        for (Pane pane : Arrays.asList(cell07, cell08, cell09, cell10, cell06)) {
            pane.setDisable(true);
            pane.setCursor(Cursor.OPEN_HAND);
        }
        try {
            URL resourceUrl = getClass().getResource("/main/demo/music/gameMusic.mp3");
            if (resourceUrl != null) {
                this.media = new Media(resourceUrl.toURI().toString());
                this.mediaPlayer = new MediaPlayer(media);
                System.out.println("Music input successfully");
            } else {
                System.out.println("Music file not found");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void adjustMusic(MouseEvent event) {
        if (this.playMusic) {
            this.playMusic = false;
            Mute.setVisible(false);
            unMute.setVisible(true);
        } else {
            this.playMusic = true;
            Mute.setVisible(true);
            unMute.setVisible(false);
        }
        if (this.playMusic) {
            this.mediaPlayer.play();
            System.out.println("Music on");
        } else {
            this.mediaPlayer.stop();
            System.out.println("Music stop");
        }
    }

    @FXML
    void cellChosen(MouseEvent event) {
        Pane paneChosen = (Pane) event.getPickResult().getIntersectedNode();
        ObservableList<Node> childElements = paneChosen.getChildren();

        String id = paneChosen.getId();
        int index = Integer.parseInt(id.substring(id.length() - 2));
        System.out.println(index);


        for (ImageView imageView : Arrays.asList(leftDirectionCell00, leftDirectionCell01, leftDirectionCell02, leftDirectionCell03, leftDirectionCell04,
                leftDirectionCell06, leftDirectionCell07, leftDirectionCell08, leftDirectionCell09, leftDirectionCell10,
                rightDirectionCell00, rightDirectionCell01, rightDirectionCell02, rightDirectionCell03, rightDirectionCell04,
                rightDirectionCell06, rightDirectionCell07, rightDirectionCell08, rightDirectionCell09, rightDirectionCell10)) {
            imageView.setVisible(false);
        }

        for (Button button : Arrays.asList(leftButtonCell00, leftButtonCell01, leftButtonCell02, leftButtonCell03, leftButtonCell04,
                leftButtonCell06, leftButtonCell07, leftButtonCell08, leftButtonCell09, leftButtonCell10,
                rightButtonCell00, rightButtonCell01, rightButtonCell02, rightButtonCell03, rightButtonCell04,
                rightButtonCell06, rightButtonCell07, rightButtonCell08, rightButtonCell09, rightButtonCell10)) {
            button.setVisible(false);
        }

        for (Node node : childElements) {
            node.setVisible(true);
            if (node instanceof Button) {
                node.setCursor(Cursor.HAND);
            }
        }
    }

    @FXML
    void leftDirectionChosen(ActionEvent event) {
        Pane paneChosen = (Pane) ((Node) event.getTarget()).getParent();
        System.out.println("pane chosen: " + paneChosen);
        String id = paneChosen.getId();
        int index = Integer.parseInt(id.substring(id.length() - 2));
        markedPane = findPane(index);

        // Disable every cells and hide every direction to play movement
        for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04, cell06, cell07, cell08, cell09, cell10)) {
            pane.setDisable(true);
        }

        for (ImageView imageView : Arrays.asList(leftDirectionCell00, leftDirectionCell01, leftDirectionCell02, leftDirectionCell03, leftDirectionCell04,
                leftDirectionCell06, leftDirectionCell07, leftDirectionCell08, leftDirectionCell09, leftDirectionCell10,
                rightDirectionCell00, rightDirectionCell01, rightDirectionCell02, rightDirectionCell03, rightDirectionCell04,
                rightDirectionCell06, rightDirectionCell07, rightDirectionCell08, rightDirectionCell09, rightDirectionCell10)) {
            imageView.setVisible(false);
        }

        for (Button button : Arrays.asList(leftButtonCell00, leftButtonCell01, leftButtonCell02, leftButtonCell03, leftButtonCell04,
                leftButtonCell06, leftButtonCell07, leftButtonCell08, leftButtonCell09, leftButtonCell10,
                rightButtonCell00, rightButtonCell01, rightButtonCell02, rightButtonCell03, rightButtonCell04,
                rightButtonCell06, rightButtonCell07, rightButtonCell08, rightButtonCell09, rightButtonCell10)) {
            button.setVisible(false);
        }

        // Move setup for current Player
        if (isP1Turn) {
            this.currentPlayer = this.player1;
            currentPlayer.moveSetup(index, -1);
        } else {
            this.currentPlayer = this.player2;
            currentPlayer.moveSetup(index, 1);
        }
        isWaitMove = false;




        Move();

    }

    @FXML
    void rightDirectionChosen(ActionEvent event) {
        Pane paneChosen = (Pane) ((Node) event.getTarget()).getParent();
        System.out.println("pane chosen: " + paneChosen);
        String id = paneChosen.getId();
        int index = Integer.parseInt(id.substring(id.length() - 2));
        markedPane = findPane(index);

        // Disable every cells and hide every direction to play movement
        for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04, cell06, cell07, cell08, cell09, cell10)) {
            pane.setDisable(true);
        }

        for (ImageView imageView : Arrays.asList(leftDirectionCell00, leftDirectionCell01, leftDirectionCell02, leftDirectionCell03, leftDirectionCell04,
                leftDirectionCell06, leftDirectionCell07, leftDirectionCell08, leftDirectionCell09, leftDirectionCell10,
                rightDirectionCell00, rightDirectionCell01, rightDirectionCell02, rightDirectionCell03, rightDirectionCell04,
                rightDirectionCell06, rightDirectionCell07, rightDirectionCell08, rightDirectionCell09, rightDirectionCell10)) {
            imageView.setVisible(false);
        }

        for (Button button : Arrays.asList(leftButtonCell00, leftButtonCell01, leftButtonCell02, leftButtonCell03, leftButtonCell04,
                leftButtonCell06, leftButtonCell07, leftButtonCell08, leftButtonCell09, leftButtonCell10,
                rightButtonCell00, rightButtonCell01, rightButtonCell02, rightButtonCell03, rightButtonCell04,
                rightButtonCell06, rightButtonCell07, rightButtonCell08, rightButtonCell09, rightButtonCell10)) {
            button.setVisible(false);
        }

        // Move setup for current Player
        if (isP1Turn) {
            this.currentPlayer = this.player1;
            currentPlayer.moveSetup(index, 1);
        } else {
            this.currentPlayer = this.player2;
            currentPlayer.moveSetup(index, -1);
        }
        isWaitMove = false;
        Move();

    }

    private int curIndex;
    private int nextIndex;
    public  void Move(){
        curIndex = currentPlayer.getCurPos();
        int direction = currentPlayer.getDirection();

        markedPane = findPane(curIndex);
        System.out.println(markedPane.getId());
        Cell takenCell = board.getCells()[curIndex];

        Timeline moveAnimation = new Timeline();

        if (!takenCell.getStonesInCell().isEmpty()) {
            currentPlayer.drawStones((Square) takenCell);

            moveAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(DURATION_TIME), (ActionEvent event1) -> {
                curIndex = Math.floorMod(curIndex + direction, 12);
                Cell cur = board.getCells()[curIndex];
                Pane curPane = findPane(curIndex);
                for (Node n : markedPane.getChildren()) {
                    if (n instanceof ImageView) {
                        String s = n.getId();
                        if (s.contains("stone")) {
                            System.out.println(markedPane.getId());
                            System.out.println(curPane.getId());
                            System.out.println("Releasing Stones...");

                            markedPane.getChildren().remove(n);
                            curPane.getChildren().add(n);
                            currentPlayer.releaseStone(cur);
                            resetBoard(board);
                            break;
                        }
                    }
                }
            }));

            moveAnimation.setCycleCount(currentPlayer.getInHand().size());
        }
        else {
            moveAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(DURATION_TIME), (ActionEvent event1) -> {
                curIndex = Math.floorMod(curIndex - direction, 12);
            }));
        }

        moveAnimation.play();

        moveAnimation.setOnFinished(event -> {
            PauseTransition delay1 = new PauseTransition(Duration.seconds(DURATION_TIME));
            delay1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    curIndex = Math.floorMod(curIndex + direction, 12);
                    nextIndex = Math.floorMod(curIndex + direction, 12);
                    try {
                        CasesDivided();
                    } catch (ContinueMoveException e1) {
                        PauseTransition delay = new PauseTransition(Duration.seconds(DURATION_TIME));
                        delay.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    System.out.println("Continue to move");

                                    Move();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        delay.play();
                    } catch (GetStonesandStopException e2) {
                        PauseTransition delay = new PauseTransition(Duration.seconds(DURATION_TIME));
                        delay.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                curIndex = Math.floorMod(curIndex + direction, 12);
                                Cell cur = board.getCells()[curIndex];

                                currentPlayer.captureStones(cur, true);
                                resetBoard(board);
                                setScore();

                                Supplier<Pane> targetBagSupplier = () -> currentPlayer.equals(player1) ? Player1bag : Player2bag;
                                Pane sourcePane = findPane(curIndex);
                                Pane targetPane = targetBagSupplier.get();

                                SequentialTransition collectingSequentialTransition = new SequentialTransition();
                                for (Node n : sourcePane.getChildren()) {
                                    if (n instanceof ImageView) {
                                        String s = n.getId();
                                        if (s.contains("stone") || s.contains("big")) {
                                            System.out.println("Collecting Stones and Stop");
                                            double fromX = n.getLayoutX();
                                            double fromY = n.getLayoutY();

                                            // Calculate the destination coordinates within the targetPane's coordinate space
                                            double toX = fromX + (targetPane.getLayoutX() - sourcePane.getLayoutX());
                                            double toY = fromY + (targetPane.getLayoutY() - sourcePane.getLayoutY());

                                            System.out.println("Moving node from (" + fromX + ", " + fromY + ") to (" + toX + ", " + toY + ")");

                                            KeyValue x = new KeyValue(n.translateXProperty(), toX - fromX, Interpolator.LINEAR);
                                            KeyValue y = new KeyValue(n.translateYProperty(), toY - fromY, Interpolator.LINEAR);
                                            KeyFrame frame = new KeyFrame(Duration.millis(200), x, y);
                                            Timeline timeline = new Timeline(frame);

                                            timeline.setOnFinished(e -> {
                                                System.out.println("Transition finished");
                                                sourcePane.getChildren().remove(n);
                                                targetPane.getChildren().add(n);
                                                n.setTranslateX(0);
                                                n.setTranslateY(0);
                                                n.setLayoutX(fromX);
                                                n.setLayoutY(fromY);
                                            });


                                            collectingSequentialTransition.getChildren().add(timeline);
                                        }
                                    }
                                }
                                try {
                                    collectingSequentialTransition.play();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                currentPlayer.moveSetup(-1, 0);

                                switchTurn();
                            }
                        });
                        delay.play();
                    } catch (GetStonesandContinueException e3) {
                        PauseTransition delay = new PauseTransition(Duration.seconds(DURATION_TIME));
                        delay.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                curIndex = Math.floorMod(curIndex + direction, 12);
                                Cell cur = board.getCells()[curIndex];

                                currentPlayer.captureStones(cur, false);
                                resetBoard(board);
                                setScore();

                                Supplier<Pane> targetBagSupplier = () -> currentPlayer.equals(player1) ? Player1bag : Player2bag;
                                Pane sourcePane = findPane(curIndex);
                                Pane targetPane = targetBagSupplier.get();

                                SequentialTransition collectingSequentialTransition = new SequentialTransition();
                                for (Node n : sourcePane.getChildren()) {
                                    if (n instanceof ImageView) {
                                        String s = n.getId();
                                        if (s.contains("stone") || s.contains("big")) {
                                            System.out.println("Collecting Stones and Stop");
                                            double fromX = n.getLayoutX();
                                            double fromY = n.getLayoutY();

                                            // Calculate the destination coordinates within the targetPane's coordinate space
                                            double toX = fromX + (targetPane.getLayoutX() - sourcePane.getLayoutX());
                                            double toY = fromY + (targetPane.getLayoutY() - sourcePane.getLayoutY());

                                            System.out.println("Moving node from (" + fromX + ", " + fromY + ") to (" + toX + ", " + toY + ")");

                                            KeyValue x = new KeyValue(n.translateXProperty(), toX - fromX, Interpolator.LINEAR);
                                            KeyValue y = new KeyValue(n.translateYProperty(), toY - fromY, Interpolator.LINEAR);
                                            KeyFrame frame = new KeyFrame(Duration.millis(200), x, y);
                                            Timeline timeline = new Timeline(frame);

                                            timeline.setOnFinished(e -> {
                                                System.out.println("Transition finished");
                                                sourcePane.getChildren().remove(n);
                                                targetPane.getChildren().add(n);
                                                n.setTranslateX(0);
                                                n.setTranslateY(0);
                                                n.setLayoutX(fromX);
                                                n.setLayoutY(fromY);
                                            });
                                            collectingSequentialTransition.getChildren().add(timeline);
                                        }
                                    }
                                }
                                try {
                                    collectingSequentialTransition.play();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                Move();

                            }
                        });
                        delay.play();
                    } catch (StopMoveException e4) {
                        currentPlayer.moveSetup(-1, 0);
                        switchTurn();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            delay1.play();
        });
    }

    public  void CasesDivided( ) throws Exception{
        int direction = currentPlayer.getDirection();

        Cell cur = board.getCells()[curIndex];
        Cell next = board.getCells()[nextIndex];
        int afterIndex = Math.floorMod(curIndex + 2 * direction, 12);
        BoardCell after = board.getCells()[afterIndex];
        // End up at a big cell -> end turn
        if (cur instanceof HalfCircle){
            throw new StopMoveException();
        }
        else if (cur.countStones() > 0){
            // End up at a small cells -> continue
            throw new ContinueMoveException();
        }
        // End up at an empty cell, next cell is empty -> end turn
        else if (cur.countStones() == 0 && next.countStones() == 0){
            throw new StopMoveException();
        }
        // End up at an empty cell, next and after cells are not empty
        else if (cur.countStones() == 0 && next.countStones() > 0 && after.countStones() > 0){
            throw new GetStonesandStopException();
        }
        // End up at an empty cell, next cell is not empty, after cell is empty
        else if (cur.countStones() == 0 && next.countStones() > 0 && after.countStones() == 0){
            throw new GetStonesandContinueException();
        }
    }

    public void switchTurn(){
        if (board.gameEnd()) {
            for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04, cell06, cell07, cell08, cell09, cell10)) {
                pane.setDisable(true);
            }
            // Display end game screen
            this.mediaPlayer.stop();
            displayEndGameScreen();
        } else {
            for (ImageView imageView : Arrays.asList(leftDirectionCell00, leftDirectionCell01, leftDirectionCell02, leftDirectionCell03, leftDirectionCell04,
                    leftDirectionCell06, leftDirectionCell07, leftDirectionCell08, leftDirectionCell09, leftDirectionCell10,
                    rightDirectionCell00, rightDirectionCell01, rightDirectionCell02, rightDirectionCell03, rightDirectionCell04,
                    rightDirectionCell06, rightDirectionCell07, rightDirectionCell08, rightDirectionCell09, rightDirectionCell10)) {
                imageView.setVisible(false);
            }

            for (Button button : Arrays.asList(leftButtonCell00, leftButtonCell01, leftButtonCell02, leftButtonCell03, leftButtonCell04,
                    leftButtonCell06, leftButtonCell07, leftButtonCell08, leftButtonCell09, leftButtonCell10,
                    rightButtonCell00, rightButtonCell01, rightButtonCell02, rightButtonCell03, rightButtonCell04,
                    rightButtonCell06, rightButtonCell07, rightButtonCell08, rightButtonCell09, rightButtonCell10)) {
                button.setVisible(false);
            }
            changeTurn();

            resetBoard(board);
            setScore();
        }
    }

    public void resetBoard(Board b) {
        CellNum00.setText("" + b.getCells()[0].calPoint());
        CellNum01.setText("" + b.getCells()[1].calPoint());
        CellNum02.setText("" + b.getCells()[2].calPoint());
        CellNum03.setText("" + b.getCells()[3].calPoint());
        CellNum04.setText("" + b.getCells()[4].calPoint());
        CellNum06.setText("" + b.getCells()[6].calPoint());
        CellNum07.setText("" + b.getCells()[7].calPoint());
        CellNum08.setText("" + b.getCells()[8].calPoint());
        CellNum09.setText("" + b.getCells()[9].calPoint());
        CellNum10.setText("" + b.getCells()[10].calPoint());
        bigcellNum05.setText("" + b.getCells()[5].calPoint());
        bigcellNum11.setText("" + b.getCells()[11].calPoint());
    }

    public void changeTurn() {
        if (isP1Turn) {
            isP1Turn = !isP1Turn;
            // Disable cells on Player 1 when it's not Player 1's turn
            for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04)) {
                pane.setDisable(true);
            }
            // Check if cells on Player 2's side are out of stone
            if (outOfStone()) {
                spread(); // Spread stones on current player's side when there is no more stones in the cells to play
                for (Pane pane : Arrays.asList(cell06, cell07, cell08, cell09, cell10)) {
                    pane.setDisable(false);
                }
            } else {
                for (Pane pane : Arrays.asList(cell06, cell07, cell08, cell09, cell10)) {
                    pane.setDisable(isPaneEmpty(pane));
                }
            }
        } else {
            isP1Turn = !isP1Turn;
            // Disable cells on Player 2 when it's not Player 2's turn
            for (Pane pane : Arrays.asList(cell06, cell07, cell08, cell09, cell10)) {
                pane.setDisable(true);
            }
            // Check if cells on Player 1's side are out of stone
            if (outOfStone()) {
                spread(); // Spread stones on current player's side when there is no more stones in the cells to play
                for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04)) {
                    pane.setDisable(false);
                }
            } else {
                for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04)) {
                    pane.setDisable(isPaneEmpty(pane));
                }
            }
        }
    }

    // Method to find which pane currently stone is in
    public Pane findPane(int index) {
        for (Pane pane : Arrays.asList(cell00, cell01, cell02, cell03, cell04, cell06, cell07, cell08, cell09, cell10, bigcell05, bigcell11)) {
            String id = pane.getId();
            int i = Integer.parseInt(id.substring(id.length() - 2));
            if (i == index) {
                return pane;
            }
        }
        return null;
    }

    public boolean outOfStone() {
        int sum = 0;
        // Calculate total stones in cells based on whose turn it is
        if (isP1Turn) {
            for (int i = 0; i < 5; i++) {
                sum += board.getCells()[i].countStones();
            }
        } else {
            for (int i = 6; i < 11; i++) {
                sum += board.getCells()[i].countStones();
            }
        }
        // Check if total stones are zero
        return (sum == 0);
    }

    public void spreadAStone(Pane sourcePane, Pane targetPane){
        for (Node n : sourcePane.getChildren()) {
            if (n instanceof ImageView) {
                String s = n.getId();
                if (s.contains("stone")) {
                    double fromX = n.getLayoutX();
                    double fromY = n.getLayoutY();

                    // Calculate the destination coordinates within the targetPane's coordinate space
                    double toX = fromX + (targetPane.getLayoutX() - sourcePane.getLayoutX());
                    double toY = fromY + (targetPane.getLayoutY() - sourcePane.getLayoutY());

                    System.out.println("Moving node from (" + fromX + ", " + fromY + ") to (" + toX + ", " + toY + ")");

                    KeyValue x = new KeyValue(n.translateXProperty(), toX - fromX, Interpolator.LINEAR);
                    KeyValue y = new KeyValue(n.translateYProperty(), toY - fromY, Interpolator.LINEAR);
                    KeyFrame frame = new KeyFrame(Duration.millis(200), x, y);
                    Timeline timeline = new Timeline(frame);
                    sourcePane.getChildren().remove(n);

                    timeline.setOnFinished(e -> {
                        System.out.println("Transition finished");
                        targetPane.getChildren().add(n);
                        n.setTranslateX(0);
                        n.setTranslateY(0);
                        n.setLayoutX(fromX);
                        n.setLayoutY(fromY);
                    });
                    timeline.play();
                    break;
                }
            }
        }
    }
    public void spread() {
        // Spread stones on whose turn it is
        if (isP1Turn) {
            if (player1.calPoint() >= 5) {
                for (int i = 0; i < 5; i++) {
                    board.getCells()[i].getStonesInCell().add(new SmallStone());
                }
            } else {
                displayEndGameScreen();
            }
        } else {
            if (player2.calPoint() >= 5) {
                for (int i = 6; i < 11; i++) {
                    board.getCells()[i].getStonesInCell().add(new SmallStone());
                }

            } else {
                displayEndGameScreen();
            }
        }
        if (isP1Turn) {
            spreadAStone(Player1bag, cell00);
            spreadAStone(Player1bag, cell01);
            spreadAStone(Player1bag, cell02);
            spreadAStone(Player1bag, cell03);
            spreadAStone(Player1bag, cell04);
        }
        else {
            spreadAStone(Player2bag, cell06);
            spreadAStone(Player2bag, cell07);
            spreadAStone(Player2bag, cell08);
            spreadAStone(Player2bag, cell09);
            spreadAStone(Player2bag, cell10);

        }
        resetBoard(board);
        setScore();
    }


    public void resetPlayScreen() {
        board = new Board();

        player1 = new Player();
        player2 = new Player();

        isP1Turn = true;
        isWaitMove = true;

    }

    public boolean isPaneEmpty(Pane pane) {
        boolean empty = false;
        Cell[] CellsOnBoard = board.getCells();
        String id = pane.getId();
        int index = Integer.parseInt(id.substring(id.length() - 2));
        if (CellsOnBoard[index].countStones() == 0) {
            empty = true;
        }
        return empty;
    }

    public void setScore() {
        Player1Score.setText("" + this.player1.calPoint());
        Player2Score.setText("" + this.player2.calPoint());
    }

    public Label getWinner(Label winner) {
        // Compare points of both players
        if (this.player1.calPoint() > this.player2.calPoint()) {
            winner.setText("1");
            return winner; // Player 1 wins
        } else if (this.player2.calPoint() > this.player1.calPoint()) {
            winner.setText("2");
            return winner; // Player 2 wins
        } else {
            winner.setText("1&2");
            return winner; // It's a tie
        }
    }

    public void displayEndGameScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndGameScreen.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EndGameScreenController controller = fxmlLoader.getController();
            Label winnerLabel = controller.getWinnerLabel();
            getWinner(winnerLabel);  // Set the winner in the controller

            Stage endGameStage = new Stage();
            endGameStage.setTitle("End Game Screen");
            endGameStage.setScene(scene);
            Image icon = new Image("main/demo/icon.jpg");
            endGameStage.getIcons().add(icon);
            endGameStage.setResizable(false);
            endGameStage.show();

            endGameStage.setOnCloseRequest(event -> {
                event.consume();
                logout(endGameStage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure to logout!");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }




    public static class ContinueMoveException extends Exception{
        public ContinueMoveException() {
        }
    }
    public static class GetStonesandContinueException extends Exception{
        public GetStonesandContinueException() {
        }
    }
    public static class GetStonesandStopException extends Exception{
        public GetStonesandStopException() {
        }
    }
    public static class StopMoveException extends Exception{
        public StopMoveException() {
        }
    }

}