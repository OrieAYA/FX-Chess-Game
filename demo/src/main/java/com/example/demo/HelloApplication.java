package com.example.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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


    /*
    @Override
    public void start(Stage stage) throws IOException {
        MenuButton menu = new MenuButton();
        GridPane root = new GridPane();

        GridPane node1 = new GridPane();
        StackPane node2 = new StackPane();
        GridPane node3 = new GridPane();

        root.setAlignment(Pos.CENTER);

        //Node 1
        node1.setAlignment(Pos.BASELINE_LEFT);
        node1.setPadding(new Insets(100,40,0,0));

        node1.add(new Text("Login"),0,0);
        node1.add(new TextField(),1,0);

        node1.add(new Text("Password"),0,1);
        node1.add(new PasswordField(),1,1);

        menu.setText("Pays");
        menu.getItems().add(new MenuItem("France"));
        menu.getItems().add(new MenuItem("USA"));
        menu.getItems().add(new MenuItem("Russia"));
        node1.add(menu,0,2);

        node1.add(new Button("Valider"),1,2);

        //Node 2
        node2.setAlignment(Pos.CENTER);
        node2.setPadding(new Insets(40,40,40,40));

        node2.getChildren().add(new Sphere(100));
        node2.getChildren().add(new Text("Interfaces !"));

        //Node 3
        node3.setAlignment(Pos.BASELINE_RIGHT);
        node3.setPadding(new Insets(100,40,0,0));

        node3.add(new CheckBox("Option 1"),0,0);
        node3.add(new CheckBox("Option 2"),0,1);
        node3.add(new CheckBox("Option 3"),0,2);

        root.add(node1,0,0);
        root.add(node2,1,0);
        root.add(node3,2,0);

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root,1000,500);//fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {

    GridPane root = new GridPane();
        GridPane btn = new GridPane();
        Button insert = new Button("insert");
        Button delete = new Button("delete");
        Button close = new Button("close");
        TextArea text = new TextArea();

        EventHandler<MouseEvent> add = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text.appendText("Simon");
            }
        };
        insert.addEventFilter(MouseEvent.MOUSE_PRESSED, add);
        EventHandler<MouseEvent> del = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text.deletePreviousChar();
            }
        };
        delete.addEventFilter(MouseEvent.MOUSE_PRESSED, del);
        EventHandler<MouseEvent> cl = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        };
        close.addEventFilter(MouseEvent.MOUSE_PRESSED, cl);

        root.add(text,0,0);
        btn.add(insert,0,0);
        btn.add(delete,1,0);
        btn.add(close,2,0);
        root.add(btn,0,1);


        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root,1000,1000);//fxmlLoader.load(), 320, 240);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

     */

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

        GridPane root = new GridPane();
        StackPane node;

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){

                node = new StackPane();
                node.getChildren().add(new ImageView(new Image(paths.get(mat[i][j]))));
                node.setPadding(new Insets(20,20,20,20));

                if(white)node.addEventFilter(MouseEvent.MOUSE_PRESSED, showMoovesW(i, j, node, root));
                else node.addEventFilter(MouseEvent.MOUSE_PRESSED, showMoovesB(i, j, node, root));

                root.add(node, j, i);

            }
        }

        setOriginalColors(root);

        return root;
    }

    private EventHandler showMoovesB(int i, int j, StackPane node, GridPane root){

        int finalI = i;
        int finalJ = j;
        StackPane finalNode = node;

        EventHandler<MouseEvent> showMooves = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setOriginalColors(root);

                switch (mat[finalI][finalJ]){
                    case "BP":
                        possibleMoove(findNode(root,finalJ,finalI+1), finalNode, root);
                        if(finalI==1){
                            possibleMoove(findNode(root,finalJ,finalI+2), finalNode, root);
                        }
                        break;
                }

            }
        };

        return showMooves;
    }

    private EventHandler showMoovesW(int i, int j, StackPane node, GridPane root){

        int finalI = i;
        int finalJ = j;
        StackPane finalNode = node;

        EventHandler<MouseEvent> showMooves = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setOriginalColors(root);

                switch (mat[finalI][finalJ]){
                    case "WP":
                        possibleMoove(findNode(root,finalJ,finalI-1), finalNode, root);
                        if(finalI==6){
                            possibleMoove(findNode(root,finalJ,finalI-2), finalNode, root);
                        }
                        break;
                }

            }
        };

        return showMooves;
    }

    private void possibleMoove(Node node, StackPane act, GridPane root){

        EventHandler<MouseEvent> moove = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                StackPane n = (StackPane) node;
                mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)];

                mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)] = "";

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

    private Node findNode(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void game(Stage stage){

        stage.setScene(new Scene(setup(w = !w),520,520));
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
        stage.setScene(new Scene(setup(w = !w),520,520));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}