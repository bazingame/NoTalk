package com.notalk.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;

public class MainContentTalkController{
    private RootLayoutController rootLayoutController;

    @FXML
    private Label nickName;

    /*
    *
    * */

    public void setRootLayoutController(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }


    /*初始化聊天界面*/
    public void loadInfo(HashMap<String,String> info){
        nickName.setText(info.get("name"));
    }



}
