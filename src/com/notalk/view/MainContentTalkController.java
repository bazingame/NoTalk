package com.notalk.view;

import com.google.gson.Gson;
import com.notalk.model.TcpClientThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainContentTalkController{
    private RootLayoutController rootLayoutController;
    private TcpClientThread client;
    private Gson gson = new Gson();

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

    public void setClient(TcpClientThread client) {
        this.client = client;
    }

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
    * 发送点对点普通消息
    * */
    @FXML
    private void sendMsg(String mysid,String tosid){
        /*获取输入框消息*/
        String msgContent = this.msgContent.getText();
        if(msgContent.length()==0){
            return;
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
        String time = format.format(date);
        HashMap<String,String> msgHashMap = new HashMap<String,String>();
        msgHashMap.put("mysid",mysid);
        msgHashMap.put("tosid",tosid);
        msgHashMap.put("time",time);
        msgHashMap.put("content",msgContent);
        /*发送至服务器*/
        this.client.sendMsg(gson.toJson(msgHashMap));
        /*清除输入框*/
        this.msgContent.clear();
        /*加入到记录框*/

        /*发送成功后记录至数据库*/

    }



}
