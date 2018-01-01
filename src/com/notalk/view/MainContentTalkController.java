package com.notalk.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class MainContentTalkController{
    private RootLayoutController rootLayoutController;

    @FXML
    private Label nickName;

    @FXML
    private VBox peopleBorderPaneList;

    @FXML
    private Button sendMsgBtn;

    @FXML
    private TextArea msgContent;

    /*
    *
    * */

    public void setRootLayoutController(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }


    /*初始化聊天界面*/
    public void loadInfo(HashMap<String, String> info) {
        nickName.setText(info.get("name"));
        BorderPane peopleBorderPane = this.creatTalkList("123",info.get("name"),"Last Words");
        peopleBorderPaneList.getChildren().add(peopleBorderPane);
    }

    /*正在聊天联系人列表单独Hbox生成*/
    public BorderPane creatTalkList(String headAddress,String nickName,String lastWord){
        BorderPane peopleBorderPane =  new BorderPane();
        peopleBorderPane.getStyleClass().addAll("talk-people-BorderPane");

        /*联系人右侧昵称和最后发言BorderPane容器*/
        BorderPane peopleBorderPaneRight =  new BorderPane();
        peopleBorderPaneRight.getStyleClass().addAll("talk-people-BorderPane-Right","contacts-list-border");
//                  peopleBorderPaneRight.getStyleClass().addAll("contacts-list-border");

        Pane headPane = new Pane();
        headPane.getStyleClass().addAll("people-headPane");
//                  ImageView headPic = new ImageView();
        Label nickNameLabel = new Label();
        nickNameLabel.setId("nickName");
        nickNameLabel.getStyleClass().addAll("label-talk-view");
        Label lastWordLabel = new Label();
        lastWordLabel.getStyleClass().addAll("label-talk-view-content");

        nickNameLabel.setText(nickName);
        lastWordLabel.setText(lastWord);
        peopleBorderPaneRight.setTop(nickNameLabel);
        peopleBorderPaneRight.setBottom(lastWordLabel);

        peopleBorderPane.setLeft(headPane);
        peopleBorderPane.setRight(peopleBorderPaneRight);

        return peopleBorderPane;
    }

    /*
    * 发送消息咯
    * */
    @FXML
    private void sendMsg(){
        /*获取输入框消息*/
        String msgContent = this.msgContent.getText();
        System.out.println(msgContent);
        /*发送至服务器*/

        /*清除输入框*/
        this.msgContent.clear();
        /*加入到记录框*/

        /*发送成功后记录至数据库*/

    }



}
