package com.example.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    final String Wpon = "file:WPon.png";
    final String WTower = "file:WTower.png";
    final String WKnight = "file:WKnight.png";
    final String WFou = "file:WFou.png";
    final String WKing = "file:WKing.png";
    final String WQueen = "file:WQueen.png";

    final String Bpon = "file:BPon.png";
    final String BTower = "file:BTower.png";
    final String BKnight = "file:BKnight.png";
    final String BFou = "file:BFou.png";
    final String BKing = "file:BKing.png";
    final String BQueen = "file:BQueen.png";

    final String noPiece = "file:nopiece.png";

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

    List<EventHandler<MouseEvent>> moovements = new ArrayList<>();

    private GridPane setup(){
        GridPane root = new GridPane();
        StackPane node;
        String path = "";

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){

                node = new StackPane();

                if(i == 0){
                    switch(j+1){
                        case 1:
                        case 8:
                            path = BTower;
                            break;
                        case 2:
                        case 7:
                            path = BKnight;
                            break;
                        case 3:
                        case 6:
                            path = BFou;
                            break;
                        case 4:
                            path = BKing;
                            break;
                        case 5:
                            path = BQueen;
                            break;
                    }
                } else if (i == 1) {
                    path = Bpon;
                }
                else if(i == 7){
                    switch(j+1){
                        case 1:
                        case 8:
                            path = WTower;
                            break;
                        case 2:
                        case 7:
                            path = WKnight;
                            break;
                        case 3:
                        case 6:
                            path = WFou;
                            break;
                        case 4:
                            path = WKing;
                            break;
                        case 5:
                            path = WQueen;
                            break;
                    }
                } else if (i == 6) {
                    path = Wpon;
                }
                else{
                    path = noPiece;
                }

                node.getChildren().add(new ImageView(new Image(path)));

                node.setPadding(new Insets(20,20,20,20));

                node.addEventFilter(MouseEvent.MOUSE_PRESSED, showMooves(i, j, node, root));

                root.add(node, j, i);

            }

        }

        setOriginalColors(root);

        return root;
    }

    //Montrer les mouvements ( cases jaunes )
    private EventHandler showMooves(int i, int j, StackPane node, GridPane root){

        int finalI = i;
        int finalJ = j;
        StackPane finalNode = node;

        EventHandler<MouseEvent> showMooves = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {



                setOriginalColors(root);

                switch (mat[finalI][finalJ]){
                    case "BP":
                        possibleMoove(findNode(root,finalJ,finalI+1), finalNode, Bpon, this, root);
                        if(finalI==1){
                            possibleMoove(findNode(root,finalJ,finalI+2), finalNode, Bpon, this, root);
                        }
                        break;
                }
            }
        };

        return showMooves;
    }

    //Faire un mouvement ( déplacer )
    private void possibleMoove(Node node, StackPane act, String path, EventHandler show, GridPane root){

        EventHandler<MouseEvent> moove = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                StackPane n = (StackPane) node;
                n.getChildren().clear();
                n.getChildren().add(new ImageView(new Image(path)));
                mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = "BP";
                n.addEventFilter(MouseEvent.MOUSE_PRESSED, showMooves(GridPane.getRowIndex(n), GridPane.getColumnIndex(n), n, root));

                mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)] = "";
                act.getChildren().clear();
                act.getChildren().add(new ImageView(new Image(noPiece)));
                act.removeEventFilter(MouseEvent.MOUSE_PRESSED, show);
            }
        };

        node.setStyle("-fx-background-color: YELLOW");
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, moove);
        moovements.add(moove);
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

    @Override
    public void start(Stage stage) throws IOException {

        GridPane root = setup();

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root,520,520);//fxmlLoader.load(), 320, 240);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}