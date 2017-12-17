package com.notalk;

import com.notalk.view.LoginController;
import com.notalk.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class MainApp extends Application {
    private Stage primaryStage;
    private AnchorPane LoginScene;
    private BorderPane TalkScene;
    private AnchorPane TalkContentScene;
//    private
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("NoTalk");
        primaryStage.getIcons().add(new Image("file:resources/images/Talk/search.png"));
        primaryStage.setWidth(1140);
        primaryStage.setHeight(800);
//        primaryStage.setMinWidth(1070);

//        initLogin();
        initRootLayout();
//        initMainContentTalk();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initLogin(){
        try {
            /*从FXML文档加载对象层次结构*/
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            LoginScene  = (AnchorPane)loader.load();

            Scene scene = new Scene(LoginScene);
            primaryStage.setScene(scene);

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            TalkScene = (BorderPane) loader.load();

            Scene scene = new Scene(TalkScene);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            controller.loadPane();

            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initMainContentTalk() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainContentTalk.fxml"));
            TalkContentScene = (AnchorPane)loader.load();

            TalkScene.setCenter(TalkContentScene);
//            Scene scene = new Scene(TalkContentScene);
//            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
