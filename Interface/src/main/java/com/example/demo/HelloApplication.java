package com.example.demo;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {

    List<String[]> volunteers = new ArrayList<>();

    ScrollPane list = new ScrollPane();;

    Stage stage;

    private void actList(){

        GridPane members = new GridPane();

        int index = 0;
        for(String[] person : volunteers){
            GridPane p = new GridPane();

            //Name
            StackPane Name = new StackPane();
            Text name = new Text(person[0]);
            name.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
            Name.getChildren().add(name);
            Name.setPadding(new Insets(20,20,20,20));
            Name.setAlignment(Pos.CENTER);
            p.add(Name, 0, 0);

            //Name
            StackPane SurName = new StackPane();
            Text Surname = new Text(person[1]);
            Surname.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
            SurName.getChildren().add(Surname);
            SurName.setPadding(new Insets(20,20,20,20));
            SurName.setAlignment(Pos.CENTER);
            p.add(SurName, 1, 0);

            //Remove
            StackPane button = new StackPane();
            Button remove = new Button("Remove");
            EventHandler<MouseEvent> removeP = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    volunteers.remove(person);
                    actList();
                }
            };
            remove.addEventHandler(MouseEvent.MOUSE_PRESSED, removeP);
            button.getChildren().add(remove);
            button.setPadding(new Insets(20,20,20,20));
            button.setAlignment(Pos.CENTER);
            p.add(button, 2, 0);

            members.add(p, 0, index);

            index++;
        }

        list.setContent(members);

    }

    @Override
    public void start(Stage st) throws IOException {

        stage = st;

        stage.setTitle("Volunteers List");

        //Settings
        GridPane front = new GridPane();
        front.setAlignment(Pos.CENTER);

        StackPane Title = new StackPane();
        Text title = new Text("Volunteers List");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 25px;");
        Title.getChildren().add(title);
        Title.setPadding(new Insets(20,20,20,20));
        Title.setAlignment(Pos.CENTER);
        front.add(Title, 0, 0);

        list.setPrefWidth(400);
        list.setPrefHeight(500);
        front.add(list,0,1);

        //TextFields
        GridPane textFields = new GridPane();

        TextField name = new TextField();
        TextField surname = new TextField();

        Text Name = new Text("Name");
        Name.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        Text Surname = new Text("Surname");
        Surname.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        textFields.add(Name,0,0);
        textFields.add(Surname,0,1);
        textFields.add(name,1,0);
        textFields.add(surname,1,1);

        textFields.setPadding(new Insets(20,20,20,20));
        textFields.setAlignment(Pos.CENTER);

        front.add(textFields,0,2);

        //Buttons
        GridPane buttons = new GridPane();

        Button add = new Button("Add");

        EventHandler<MouseEvent> addMember = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String[] person = {name.getText(), surname.getText()};
                volunteers.add(person);
                actList();
            }
        };

        add.addEventHandler(MouseEvent.MOUSE_PRESSED, addMember);
        add.setPadding(new Insets(20,20,20,20));

        Button close = new Button("Close");

        EventHandler<MouseEvent> closeApp = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        };

        close.addEventHandler(MouseEvent.MOUSE_PRESSED, closeApp);
        close.setPadding(new Insets(20,20,20,20));

        buttons.add(add,0,0);
        buttons.add(close,1,0);

        buttons.setAlignment(Pos.CENTER);

        front.add(buttons,0,3);

        stage.setScene(new Scene(front, 500, 800));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}