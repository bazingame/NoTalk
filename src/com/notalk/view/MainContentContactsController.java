package com.notalk.view;

import com.notalk.model.People;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jdk.nashorn.internal.ir.VarNode;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainContentContactsController {
    Collection<People> peopleList = new ArrayList();


    @FXML private VBox ContactsList;

    @FXML private  ScrollPane scrollPane;

    /*
    *
    * 添加联系人
    *
    * */
    @FXML
    public void addPeople(){
        People people1 = new People("Howard","2016501308");
        People people2 = new People("Howard","2016501308");
        peopleList.add(people1);
        peopleList.add(people2);
/*----------------------------------------------------set1*/
        /*先添加分组列表，每一组为一个TitledPane*/
        TitledPane titledPane = new TitledPane();
        titledPane.setText("Friends");
        titledPane.setStyle("-fx-font-size: 18px");
        /*添加联系人*/
        VBox peopleSetVbox = new VBox();
        peopleSetVbox.setStyle("-fx-background-color: #EEEFF3");

        /*每个联系人一个peopleBorderPane*/
        for(int i = 0;i < 20;i++){
            BorderPane peopleBorderPane =  new BorderPane();
            peopleBorderPane.getStyleClass().addAll("people-BorderPane");

        /*联系人右侧昵称和最后发言BorderPane容器*/
            BorderPane peopleBorderPaneRight =  new BorderPane();
            peopleBorderPaneRight.getStyleClass().addAll("people-BorderPane-Right","contacts-list-border");
//        peopleBorderPaneRight.getStyleClass().addAll("contacts-list-border");


            Pane headPane = new Pane();
            headPane.getStyleClass().addAll("people-headPane");
//        ImageView headPic = new ImageView();

            Label nickName = new Label();
            nickName.getStyleClass().addAll("label-talk-view");
            Label lastWords = new Label();
            lastWords.getStyleClass().addAll("label-talk-view-content");

            nickName.setText("Howad");
            lastWords.setText("Last Wordssss");
            peopleBorderPaneRight.setTop(nickName);
            peopleBorderPaneRight.setBottom(lastWords);

            peopleBorderPane.setLeft(headPane);
            peopleBorderPane.setRight(peopleBorderPaneRight);
            peopleSetVbox.getChildren().addAll(peopleBorderPane);

            peopleSetVbox.setOnMouseClicked(new EventHandler<Event>(){

            } );
//
//            EventHandler eventHandler = new EventHandler(){
//                @Override
//                public void handle(MouseEvent e){
//                    System.out.println("sadasda");
//                }
//            }

//            Creating the mouse event handler
//            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    System.out.println("Hello World");
//                }
//            };
//Adding event Filter
//            Circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

//            peopleSetVbox.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
        }

        titledPane.setContent(peopleSetVbox);
//        this.ContactsList.getChildren().addAll(titledPane);

/*----------------------------------------------------set1*/

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(titledPane);
    }





    @FXML
    private void initialize(){
        /*初始化联系人列表*/
        this.addPeople();
        System.out.println("addPeople OK");
        /*添加监听事件 监听任务的选择*/


    }
}
