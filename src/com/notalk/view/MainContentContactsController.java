package com.notalk.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notalk.model.DataBaseOperate;
import com.notalk.model.GroupPeople;
import com.notalk.util.Echo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//import java.awt.event.ActionEvent;
//import java.awt.event.MouseEvent;
//import java.beans.EventHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import static com.notalk.util.Echo.echo;

public class MainContentContactsController {
    private RootLayoutController rootLayoutController;
    private MainContentTalkController mainContentTalkController;
    DataBaseOperate db = new DataBaseOperate();
    Gson gson = new Gson();
    Collection<GroupPeople> peopleList = new ArrayList();

    @FXML private VBox ContactsList;

    @FXML private  ScrollPane scrollPane;

    /*
    *
    * 初始化并添加联系人
    *
    * */
    @FXML
    public void addPeople(){

        try {

            //获取联系人信息
            String friendsList = db.getFriendsList(2016501308);
            List<GroupPeople> groupPeopleList = gson.fromJson(friendsList, new TypeToken<List<GroupPeople>>() {}.getType());
//            System.out.println(gson.toJson(groupPeople));
            VBox peoplelistvBox = new VBox();
            for(int j = 0;j < groupPeopleList.size(); j++){
                GroupPeople groupPeople =groupPeopleList.get(j);
                /*先添加分组列表，每一组为一个TitledPane*/
                TitledPane titledPane = new TitledPane();
                titledPane.setText(groupPeople.getGroup_name().get(0));
                titledPane.setStyle("-fx-font-size: 18px");
                 /*添加联系人*/
                VBox peopleSetVbox = new VBox();
                peopleSetVbox.setStyle("-fx-background-color: #EEEFF3");

                /*每个联系人一个peopleBorderPane*/
                for(int i = 0;i < groupPeople.getFriend_list().size();i++){
                    GroupPeople.FriendListBean friendListBean =  groupPeople.getFriend_list().get(i);
                    BorderPane peopleBorderPane =  new BorderPane();
                    peopleBorderPane.getStyleClass().addAll("people-BorderPane");

                    /*联系人右侧昵称和最后发言BorderPane容器*/
                    BorderPane peopleBorderPaneRight =  new BorderPane();
                    peopleBorderPaneRight.getStyleClass().addAll("people-BorderPane-Right","contacts-list-border");
//                  peopleBorderPaneRight.getStyleClass().addAll("contacts-list-border");

                    Pane headPane = new Pane();
                    headPane.getStyleClass().addAll("people-headPane");
//                  ImageView headPic = new ImageView();
                    Label nickName = new Label();
                    nickName.getStyleClass().addAll("label-talk-view");
                    Label lastWords = new Label();
                    lastWords.getStyleClass().addAll("label-talk-view-content");

                    nickName.setText(friendListBean.getFriend_nickname());
                    lastWords.setText("Last Wordssss");
                    peopleBorderPaneRight.setTop(nickName);
                    peopleBorderPaneRight.setBottom(lastWords);

                    peopleBorderPane.setLeft(headPane);
                    peopleBorderPane.setRight(peopleBorderPaneRight);
                    peopleSetVbox.getChildren().addAll(peopleBorderPane);

                    /*点击联系人事件*/
                    peopleBorderPane.setOnMouseClicked(new EventHandler <MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            switchToTalk(peopleBorderPane);
                        }
                    });


                }

                titledPane.setContent(peopleSetVbox);
                peoplelistvBox.getChildren().addAll(titledPane);
            }

            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setContent(peoplelistvBox);


        } catch (SQLException e) {
            e.printStackTrace();
        }





/*----------------------------------------------------set1*/


    }

    /*
    *
    * */

    public void setRootLayoutController(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }

    public void setMainContentTalkController(MainContentTalkController mainContentTalkController){
        this.mainContentTalkController = mainContentTalkController;
    }

    /*
    * 切换到消息界面 且对话框内加载相应数据
    * */
    private void switchToTalk(BorderPane peopleBorderPane){
        System.out.println("Click Contacts");
        rootLayoutController.clickMsg();
//        String[] test = {"hhhh","asdas"};
        HashMap<String,String> infoMap = new HashMap<>();
//        peopleBorderPane.getChildren();
        peopleBorderPane.setStyle("-fx-background-color: red");
//        echo( peopleBorderPane.getRight());
        infoMap.put("name","SnoopyLikePiggy");
        infoMap.put("words","Hello");
        rootLayoutController.initTalkInfo(infoMap);
    }

    @FXML
    private void initialize(){
        /*初始化联系人列表*/
        this.addPeople();
        System.out.println("addPeople OK");
        /*添加监听事件 监听任务的选择*/


    }
}

