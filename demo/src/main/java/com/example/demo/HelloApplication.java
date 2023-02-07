package com.example.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class HelloApplication extends Application {

    final String WP = "file:WPon.png";
    final String WT = "file:WTower.png";
    final String WK = "file:WKnight.png";
    final String WF = "file:WFou.png";
    final String WKi = "file:WKing.png";
    final String WQ = "file:WQueen.png";

    final String BP = "file:BPon.png";
    final String BT = "file:BTower.png";
    final String BK = "file:BKnight.png";
    final String BF = "file:BFou.png";
    final String BKi = "file:BKing.png";
    final String BQ = "file:BQueen.png";

    final String noPiece = "file:nopiece.png";

    boolean w = false;

    Stage stage;

    final Map<String, String> paths = new HashMap<>();

    List<String> wPieces = Arrays.asList("WP","WT","WK","WF","WKi","WQ");
    List<String> bPieces = Arrays.asList("BP","BT","BK","BF","BKi","BQ");

    List<Integer> possibleValues = Arrays.asList(0,1,2,3,4,5,6,7);

    boolean canMoove = false;

    String[][] mat = {
            {"BT","BK","BF","BKi","BQ","BF","BK","BT"},
            {"BP","BP","BP","BP","BP","BP","BP","BP"},
            {"","","","","","","",""},
            {"","","","","","","",""},
            {"","","","","","","",""},
            {"","","","","","","",""},
            {"WP","WP","WP","WP","WP","WP","WP","WP"},
            {"WT","WK","WF","WKi","WQ","WF","WK","WT"}
    };

    private GridPane setup(boolean white){

        canMoove = false;

        GridPane root = new GridPane();
        StackPane node;

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){

                node = new StackPane();
                node.getChildren().add(new ImageView(new Image(paths.get(mat[i][j]))));
                node.setPadding(new Insets(20,20,20,20));

                EventHandler ev = showMooves(i, j, node, root);

                if(ev != null)canMoove = true;

                node.addEventFilter(MouseEvent.MOUSE_PRESSED, ev);

                root.add(node, j, i);

            }
        }

        setOriginalColors(root);

        return root;
    }

    private EventHandler showMooves(int i, int j, StackPane node, GridPane root){

        int finalI = i;
        int finalJ = j;
        StackPane finalNode = node;

        EventHandler<MouseEvent> showMooves = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setOriginalColors(root);

                if(!w) {

                    switch (mat[finalI][finalJ]) {

                        case "BP":
                            //Bouger
                            if (mat[finalI + 1][finalJ] == "") {
                                possibleMoove(findNode(root, finalI + 1, finalJ), finalNode, root);
                                if (finalI == 1 && mat[finalI + 2][finalJ] == "") {
                                    possibleMoove(findNode(root, finalI + 2, finalJ), finalNode, root);
                                }
                            }
                            //Manger
                            if (wPieces.contains(mat[finalI + 1][finalJ + 1])) {
                                possibleMoove(findNode(root, finalI + 1, finalJ + 1), finalNode, root);
                            }
                            if (wPieces.contains(mat[finalI + 1][finalJ - 1])) {
                                possibleMoove(findNode(root, finalI + 1, finalJ - 1), finalNode, root);
                            }
                            break;

                        case "BT":
                            towerMoove(bPieces, wPieces, finalI, finalJ, finalNode, root);
                            break;

                        case "BK":
                            knightMoove(bPieces, finalI, finalJ, finalNode, root);
                            break;

                        case "BF":
                            fouMoove(bPieces, wPieces, finalI, finalJ, finalNode, root);
                            break;

                        case "BKi":
                            kingMoove(bPieces, root, finalI, finalJ, finalNode);
                            break;

                        case "BQ":
                            queenMoove(bPieces, wPieces, finalI, finalJ, finalNode, root);
                            break;
                    }
                }

                else {

                    switch (mat[finalI][finalJ]) {
                        case "WP":
                            if (mat[finalI - 1][finalJ] == "") {
                                possibleMoove(findNode(root, finalI - 1, finalJ), finalNode, root);
                                if (finalI == 6) {
                                    possibleMoove(findNode(root, finalI - 2, finalJ), finalNode, root);
                                }
                            }
                            //Manger
                            if (bPieces.contains(mat[finalI - 1][finalJ - 1])) {
                                possibleMoove(findNode(root, finalI - 1, finalJ - 1), finalNode, root);
                            }
                            if (bPieces.contains(mat[finalI - 1][finalJ + 1])) {
                                possibleMoove(findNode(root, finalI - 1, finalJ + 1), finalNode, root);
                            }
                            break;

                        case "WT":
                            towerMoove(wPieces, bPieces, finalI, finalJ, finalNode, root);
                            break;

                        case "WK":
                            knightMoove(wPieces, finalI, finalJ, finalNode, root);
                            break;

                        case "WF":
                            fouMoove(wPieces, bPieces, finalI, finalJ, finalNode, root);
                            break;

                        case "WKi":
                            kingMoove(wPieces, root, finalI, finalJ, finalNode);
                            break;

                        case "WQ":
                            queenMoove(wPieces, bPieces, finalI, finalJ, finalNode, root);
                            break;
                    }
                }
            }
        };

        return showMooves;
    }

    private void knightMoove(List<String> Pieces, int finalI, int finalJ, StackPane finalNode, GridPane root){
        //Bouger
        if((possibleValues.contains(finalI - 2) && possibleValues.contains(finalJ + 1)) && !Pieces.contains(mat[finalI - 2][finalJ + 1]))possibleMoove(findNode(root, finalI - 2, finalJ + 1), finalNode, root);
        if((possibleValues.contains(finalI - 2) && possibleValues.contains(finalJ - 1)) && !Pieces.contains(mat[finalI - 2][finalJ - 1]))possibleMoove(findNode(root, finalI - 2, finalJ - 1), finalNode, root);
        if((possibleValues.contains(finalI - 1) && possibleValues.contains(finalJ + 2)) && !Pieces.contains(mat[finalI - 1][finalJ + 2]))possibleMoove(findNode(root, finalI - 1, finalJ + 2), finalNode, root);
        if((possibleValues.contains(finalI - 1) && possibleValues.contains(finalJ - 2)) && !Pieces.contains(mat[finalI - 1][finalJ - 2]))possibleMoove(findNode(root, finalI - 1, finalJ - 2), finalNode, root);
        if((possibleValues.contains(finalI + 1) && possibleValues.contains(finalJ + 2)) && !Pieces.contains(mat[finalI + 1][finalJ + 2]))possibleMoove(findNode(root, finalI + 1, finalJ + 2), finalNode, root);
        if((possibleValues.contains(finalI + 1) && possibleValues.contains(finalJ - 2)) && !Pieces.contains(mat[finalI + 1][finalJ - 2]))possibleMoove(findNode(root, finalI + 1, finalJ - 2), finalNode, root);
        if((possibleValues.contains(finalI + 2) && possibleValues.contains(finalJ + 1)) && !Pieces.contains(mat[finalI + 2][finalJ + 1]))possibleMoove(findNode(root, finalI + 2, finalJ + 1), finalNode, root);
        if((possibleValues.contains(finalI + 2) && possibleValues.contains(finalJ - 1)) && !Pieces.contains(mat[finalI + 2][finalJ - 1]))possibleMoove(findNode(root, finalI + 2, finalJ - 1), finalNode, root);
    }

    private void towerMoove(List<String> aPieces, List<String> ePieces, int finalI, int finalJ, StackPane finalNode, GridPane root){
        //Droite
        for(int m = finalJ + 1; m<8; m++){
            if(aPieces.contains(mat[finalI][m]))break;
            else if(ePieces.contains(mat[finalI][m])){
                possibleMoove(findNode(root, finalI, m), finalNode, root);
                break;
            }
            possibleMoove(findNode(root, finalI, m), finalNode, root);
        }
        //Gauche
        for(int m = finalJ - 1; m>=0; m--){
            if(aPieces.contains(mat[finalI][m]))break;
            else if(ePieces.contains(mat[finalI][m])){
                possibleMoove(findNode(root, finalI, m), finalNode, root);
                break;
            }
            possibleMoove(findNode(root, finalI, m), finalNode, root);
        }
        //Bas
        for(int m = finalI + 1; m<8; m++){
            if(aPieces.contains(mat[m][finalJ]))break;
            else if(ePieces.contains(mat[m][finalJ])){
                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                break;
            }
            possibleMoove(findNode(root, m, finalJ), finalNode, root);
        }
        //Haut
        for(int m = finalI - 1; m>=0; m--){
            if(aPieces.contains(mat[m][finalJ]))break;
            else if(ePieces.contains(mat[m][finalJ])){
                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                break;
            }
            possibleMoove(findNode(root, m, finalJ), finalNode, root);
        }
    }

    private void fouMoove(List<String> aPieces, List<String> ePieces, int finalI, int finalJ, StackPane finalNode, GridPane root){
        int x = finalJ + 1;
        int y = finalI - 1;
        while(x < 8 && y >= 0){
            if(aPieces.contains(mat[y][x]))break;
            else{
                possibleMoove(findNode(root, y, x), finalNode, root);
                if(ePieces.contains(mat[y][x]))break;
            }
            x++;
            y--;
        }
        x = finalJ - 1;
        y = finalI - 1;
        while(x >= 0 && y >= 0){
            if(aPieces.contains(mat[y][x]))break;
            else{
                possibleMoove(findNode(root, y, x), finalNode, root);
                if(ePieces.contains(mat[y][x]))break;
            }
            x--;
            y--;
        }
        x = finalJ + 1;
        y = finalI + 1;
        while(x < 8 && y < 8){
            if(aPieces.contains(mat[y][x]))break;
            else{
                possibleMoove(findNode(root, y, x), finalNode, root);
                if(ePieces.contains(mat[y][x]))break;
            }
            x++;
            y++;
        }
        x = finalJ - 1;
        y = finalI + 1;
        while(x >= 0 && y < 8){
            if(aPieces.contains(mat[y][x]))break;
            else{
                possibleMoove(findNode(root, y, x), finalNode, root);
                if(ePieces.contains(mat[y][x]))break;
            }
            x--;
            y++;
        }
    }

    private void queenMoove(List<String> aPieces, List<String> ePieces, int finalI, int finalJ, StackPane finalNode, GridPane root){
        //Bouger
        towerMoove(aPieces, ePieces, finalI, finalJ, finalNode, root);
        fouMoove(aPieces, ePieces, finalI, finalJ, finalNode, root);
    }

    private boolean kingMoove(List<String> Pieces, GridPane root, int finalI, int finalJ, StackPane finalNode){
        //Bouger
        boolean canMoove = false;
        if((possibleValues.contains(finalI - 1) && possibleValues.contains(finalJ - 1)) && !Pieces.contains(mat[finalI - 1][finalJ - 1])){
            possibleMoove(findNode(root, finalI - 1, finalJ - 1), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalJ - 1)) && !Pieces.contains(mat[finalI][finalJ - 1])){
            possibleMoove(findNode(root, finalI, finalJ - 1), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalI - 1)) && !Pieces.contains(mat[finalI - 1][finalJ])){
            possibleMoove(findNode(root, finalI - 1, finalJ), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalI + 1) && possibleValues.contains(finalJ - 1)) && !Pieces.contains(mat[finalI + 1][finalJ - 1])){
            possibleMoove(findNode(root, finalI + 1, finalJ - 1), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalI + 1) && possibleValues.contains(finalJ + 1)) && !Pieces.contains(mat[finalI + 1][finalJ + 1])){
            possibleMoove(findNode(root, finalI + 1, finalJ + 1), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalJ + 1)) && !Pieces.contains(mat[finalI][finalJ + 1])){
            possibleMoove(findNode(root, finalI, finalJ + 1), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalI + 1)) && !Pieces.contains(mat[finalI + 1][finalJ])){
            possibleMoove(findNode(root, finalI + 1, finalJ), finalNode, root);
            canMoove = true;
        }
        if((possibleValues.contains(finalI - 1) && possibleValues.contains(finalJ + 1)) && !Pieces.contains(mat[finalI - 1][finalJ + 1])){
            possibleMoove(findNode(root, finalI - 1, finalJ + 1), finalNode, root);
            canMoove = true;
        }
        return !canMoove;
    }

    private void possibleMoove(Node node, StackPane act, GridPane root){

        EventHandler<MouseEvent> moove = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                StackPane n = (StackPane) node;
                mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)];

                mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)] = "";

                String p = mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)];

                if(p == "WP" && GridPane.getRowIndex(n) == 0) {
                    mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = "WQ";
                } else if (p == "BP" && GridPane.getRowIndex(n) == 7) {
                    mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = "BQ";
                }

                game(stage);
            }
        };

        node.setStyle("-fx-background-color: YELLOW");
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, moove);

    }

    private void setOriginalColors(GridPane gridPane){
        for (Node node : gridPane.getChildren()) {
            if((GridPane.getColumnIndex(node)%2 == GridPane.getRowIndex(node)%2) || (GridPane.getColumnIndex(node)%2 == GridPane.getRowIndex(node)%2)){
                node.setStyle("-fx-background-color: CORNSILK");
            }
            else{
                node.setStyle("-fx-background-color: DARKGOLDENROD");
            }
        }
    }

    private Node findNode(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private boolean KiExist(String king){

        boolean is = false;

        for(String[] i : mat){
            for(String j : i){
                if(j == king)is = true;
            }
        }

        return is;
    }

    private void game(Stage stage){

        stage.setScene(new Scene(setup(w = !w), 520, 520));
        stage.show();

        if(!w && !KiExist("BKi")){
            resultBoard("White won !");
        } else if (w && !KiExist("WKi")) {
            resultBoard("Black won !");
        }

    }

    private GridPane front(){

        GridPane front = new GridPane();

        front.setStyle("-fx-background-color: DARKGOLDENROD");
        front.setAlignment(Pos.CENTER);

        StackPane backgrd = new StackPane();
        backgrd.getChildren().add(new ImageView(new Image("file:background.jpeg")));
        backgrd.setPadding(new Insets(20,20,20,20));
        backgrd.setAlignment(Pos.CENTER);

        front.add(backgrd,0,0);

        GridPane buttons = new GridPane();

        Button launch = new Button("Play");
        EventHandler<MouseEvent> launchGame = new EventHandler() {
            @Override
            public void handle(Event event) {
                game(stage);
            }
        };
        launch.addEventFilter(MouseEvent.MOUSE_PRESSED, launchGame);

        launch.setPadding(new Insets(20,20,20,20));
        launch.setAlignment(Pos.CENTER);

        buttons.add(launch, 0, 0);
        buttons.setAlignment(Pos.CENTER);

        front.add(buttons,0,1);

        return front;

    }

    private void resultBoard(String resultGame){

        GridPane board = new GridPane();

        board.setStyle("-fx-background-color: DARKGOLDENROD");
        board.setAlignment(Pos.CENTER);

        StackPane results = new StackPane();
        Text result = new Text(resultGame);
        result.setStyle("-fx-font-weight: bold; -fx-font-size: 25px;");
        results.getChildren().add(result);
        results.setPadding(new Insets(20,20,20,20));
        results.setAlignment(Pos.CENTER);

        board.add(results,0,0);

        GridPane buttons = new GridPane();

        Button launch = new Button("Play Again");
        EventHandler<MouseEvent> launchGame = new EventHandler() {
            @Override
            public void handle(Event event) {
                game(stage);
            }
        };
        launch.addEventFilter(MouseEvent.MOUSE_PRESSED, launchGame);

        launch.setPadding(new Insets(20,20,20,20));
        launch.setAlignment(Pos.CENTER);

        Button close = new Button("Close");
        EventHandler<MouseEvent> closeGame = new EventHandler() {
            @Override
            public void handle(Event event) {
                stage.close();
            }
        };
        close.addEventFilter(MouseEvent.MOUSE_PRESSED, closeGame);

        close.setPadding(new Insets(20,20,20,20));
        close.setAlignment(Pos.CENTER);

        buttons.add(launch, 0, 0);
        buttons.add(close,1,0);
        buttons.setAlignment(Pos.CENTER);

        board.add(buttons,0,1);

        stage.setScene(new Scene(board, 700, 700));
        stage.show();

    }

    @Override
    public void start(Stage st) throws IOException {

        stage = st;

        paths.put("WP", WP);
        paths.put("WT", WT);
        paths.put("WK", WK);
        paths.put("WF", WF);
        paths.put("WKi", WKi);
        paths.put("WQ", WQ);
        paths.put("WP", WP);
        paths.put("WT", WT);
        paths.put("WK", WK);
        paths.put("WF", WF);
        paths.put("WKi", WKi);
        paths.put("WQ", WQ);
        paths.put("BP", BP);
        paths.put("BT", BT);
        paths.put("BK", BK);
        paths.put("BF", BF);
        paths.put("BKi", BKi);
        paths.put("BQ", BQ);
        paths.put("", noPiece);

        stage.setTitle("Chess");
        stage.setScene(new Scene(front(), 700, 700));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}