package com.notalk.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notalk.MainApp;
import com.notalk.model.DataBaseOperate;
import com.notalk.model.GroupPeople;
import com.notalk.util.Echo;
import javafx.beans.property.Property;
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

    /**
    *
    * 初始化并添加联系人
    *
    * */
    @FXML
    public void addPeople(){

        try {

            //获取联系人信息
            String friendsList = db.getFriendsList(MainApp.Mysid);
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
                    //昵称
                    Label nickName = new Label();
                    nickName.setId("nickName");
                    nickName.getStyleClass().addAll("label-talk-view");
                    //账号标签 隐藏
                    Label friendSid = new Label();
                    friendSid.setId("friendSid");
                    friendSid.setVisible(false);
                    //最后发言
                    Label lastWords = new Label();
                    lastWords.getStyleClass().addAll("label-talk-view-content");

                    nickName.setText(friendListBean.getFriend_nickname());
                    friendSid.setText(friendListBean.getFriend_sid());
                    lastWords.setText("Last Wordssss");
                    peopleBorderPaneRight.setRight(friendSid);
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

    /**
    * 切换到消息界面 且对话框内加载相应数据
    * */
    private void switchToTalk(BorderPane peopleBorderPane){
        rootLayoutController.clickMsg();
        HashMap<String,String> infoMap = new HashMap<>();
        //获取姓名
        Label nickNameLabel = (Label)peopleBorderPane.lookup("#nickName");
        String nickNameString = nickNameLabel.getText();
        infoMap.put("name",nickNameString);
        //获取账号
        Label friendSidLabel = (Label)peopleBorderPane.lookup("#friendSid");
        String friendSidString = friendSidLabel.getText();
        infoMap.put("sid",friendSidString);
        System.out.println("name:"+nickNameString+" sid:"+friendSidString);
        //获取聊天记录
        try {
            String msgRecord = db.getMsgRecord(MainApp.Mysid,Integer.parseInt(friendSidString));
//            System.out.println(msgRecord);
            infoMap.put("record",msgRecord);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

