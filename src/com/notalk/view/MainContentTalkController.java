package com.notalk.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notalk.MainApp;
import com.notalk.model.DataBaseOperate;
import com.notalk.model.GroupPeople;
import com.notalk.model.TcpClientThread;
import com.notalk.model.p2pmsgRecord;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainContentTalkController{
    private RootLayoutController rootLayoutController;
    private TcpClientThread client;
    private DataBaseOperate db = new DataBaseOperate();
    private Gson gson = new Gson();
    private List<p2pmsgRecord> msgRecordList;
    private List<Integer> recentPeople = new ArrayList<>();

    @FXML
    private ScrollPane talkScrollPane;

    @FXML
    private VBox msgRecordListBox;

    @FXML
    private Label nickName;

    @FXML
    private Label sidLabel;


    @FXML
    private VBox peopleBorderPaneList;

    @FXML
    private Button sendMsgBtn;

    @FXML
    private TextArea msgContent;

    /**
    *构造函数 为文本域添监听回车事件
    * */
    public MainContentTalkController(){
    }

    public void setClient(TcpClientThread client) {
        this.client = client;
    }

    public void setRootLayoutController(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }


    /*初始化聊天界面*/
    public void loadInfo(HashMap<String, String> info) {
        nickName.setText(info.get("name"));
        sidLabel.setText(info.get("sid"));
        msgRecordList = gson.fromJson(info.get("record"), new TypeToken<List<p2pmsgRecord>>() {}.getType());
        this.msgRecordListBox.getChildren().clear();
        //初始化消息记录
        //TODO 缓存处理
        for(p2pmsgRecord personInfo : msgRecordList){
            HBox hBox = new HBox();
//            StackPane stackPane = new StackPane();
//            Rectangle rectangle = new Rectangle();
            Label label = new Label();
            label.setText(personInfo.getContent());
//            rectangle.setStyle("-fx-fill: red;-fx-pref-height: 50px;-fx-pref-width: 50px;-fx-arc-height:5px;-fx-arc-width:5px");
//            double height = label.widthProperty().doubleValue();
//            System.out.println(height);
//            double width = label.width();
//            rectangle.setHeight(height);
//            rectangle.setWidth(width);
//            rectangle.setFill(Color.GREEN);

//            stackPane.getChildren().addAll(rectangle,label);
            hBox.getChildren().addAll(label);
            if(Integer.parseInt(personInfo.getFromSid())==MainApp.Mysid){
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(10,80,10,10));
                label.getStyleClass().addAll("talk-sendmsg-label");
            }else{
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(10,10,10,80));
                label.getStyleClass().addAll("talk-recmsg-label");
            }

            this.msgRecordListBox.getChildren().addAll(hBox);
        }

        //double height = msgRecordListBox.getHeight();

        this.talkScrollPane.setVvalue(999999999);

        //左侧最近联系人列表
        //若Hbox中已有则转到，无则添加
        if(this.recentPeople.contains(Integer.parseInt(info.get("sid")))){
        }else{
            BorderPane peopleBorderPane = this.creatTalkList("123",info.get("name"),info.get("sid"),"Last Words");
            peopleBorderPaneList.getChildren().add(peopleBorderPane);
        }

        //为textArea添加监听回车事件
        this.msgContent.setOnKeyReleased(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.ENTER){
//                    System.out.println("Enter");
                    sendMsgBtnClick();
                    msgContent.clear();
                }
            }
        });
    }

    /**
     *正在聊天联系人列表单独Hbox生成
     **/
    public BorderPane creatTalkList(String headAddress,String nickName,String sid,String lastWord){
        //添加到list中
        this.recentPeople.add(Integer.parseInt(sid));

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
        Label sidLabel = new Label();

        nickNameLabel.setText(nickName);
        lastWordLabel.setText(lastWord);
        sidLabel.setText(sid);
        peopleBorderPaneRight.setTop(nickNameLabel);
        peopleBorderPaneRight.setBottom(lastWordLabel);

        peopleBorderPane.setLeft(headPane);
        peopleBorderPane.setRight(peopleBorderPaneRight);

        /*点击联系人事件*/
        peopleBorderPane.setOnMouseClicked(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HashMap<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("name",nickName);
                hashMap.put("sid",sid);
                try {
                    String msgRecord = db.getMsgRecord(MainApp.Mysid,Integer.parseInt(sid));
                    hashMap.put("record",msgRecord);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                loadInfo(hashMap);
            }
        });
        return peopleBorderPane;
    }



    /**
    *处理发送按钮点击事件
    * */
    @FXML
    private void sendMsgBtnClick(){
        //获取输入框消息
        String msgContent = this.msgContent.getText();
        if(msgContent.length()==0){
            return;
        }
        //消灭回车符
        StringBuffer stringBuffer = new StringBuffer(msgContent);
        if(stringBuffer.charAt(msgContent.length()-1)=='\n'){
            stringBuffer.deleteCharAt(msgContent.length()-1);
        }
        msgContent = stringBuffer.toString();
        if(msgContent.length()==0){
            return;
        }
        //获取消息类型
        //获取发送、接受者账号
        //调用发送方法
        this.sendMsg("p2p", Integer.toString(MainApp.Mysid),this.sidLabel.getText(),msgContent);


    }


    /**
    * 发送点对点普通消息
    * */
    private void sendMsg(String type,String fromsid,String tosid,String msgContent){

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
        String time = format.format(date);
        HashMap<String,String> msgHashMap = new HashMap<String,String>();
        msgHashMap.put("mysid",fromsid);
        msgHashMap.put("tosid",tosid);
        msgHashMap.put("time",time);
        msgHashMap.put("content",msgContent);
        msgHashMap.put("type","p2p");
        /*发送至服务器*/
        this.client.sendMsg(gson.toJson(msgHashMap));
        System.out.println(gson.toJson(msgHashMap));
        /*清除输入框*/
        this.msgContent.clear();
        /*加入到记录框*/
        HBox hBox = new HBox();
        Label label = new Label();
        label.setText(msgContent);
        hBox.getChildren().addAll(label);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(10,80,10,10));
        label.getStyleClass().addAll("talk-sendmsg-label");
        this.msgRecordListBox.getChildren().addAll(hBox);
        /*发送成功后记录至数据库*/
        try {
            db.sendfriendMsg(Integer.parseInt(fromsid),Integer.parseInt(tosid),msgContent,time);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //滚到最下面！
        this.talkScrollPane.setVvalue(999999999);
    }



}
