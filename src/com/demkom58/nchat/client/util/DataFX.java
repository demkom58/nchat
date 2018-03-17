package com.demkom58.nchat.client.util;

import com.demkom58.nchat.client.gui.AbstractController;
import com.demkom58.nchat.client.gui.ChatController;
import com.demkom58.nchat.client.gui.LoginController;
import com.demkom58.nchat.client.network.ClientMessenger;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;

public class DataFX {
    public static Stage stage;
    public static double xOffset;
    public static double yOffset;

    public static class Scenes {
        private static HashMap<Class, Object[]> scenesMap = new HashMap<>();

        public static Scene getScene(Class controller) {
            return getAnchorPane(controller).getScene();
        }
        public static void addScene(Class controller, FXMLLoader loader) throws Exception {
            if(scenesMap.containsKey(controller)) return;


            AnchorPane anchorPane = loader.load();
            anchorPane.setUserData(loader.getController());
            ((AbstractController)loader.getController()).setScene(new Scene(loader.getRoot()));

            scenesMap.put(controller, new Object[]{anchorPane, loader});
        }
        public static void removeScene(Class controller) {
            scenesMap.remove(controller);
        }

        public static FXMLLoader getLoader(Class controller){
            return (FXMLLoader)scenesMap.get(controller)[1];
        }
        public static AbstractController getController(Class controller){
            return (AbstractController)getAnchorPane(controller).getUserData();
        }

        public static void setAllDraggable(){
            for(Object[] objects : scenesMap.values()) {
                Scene scene = ((AnchorPane)objects[0]).getScene();
                scene.setOnMousePressed(event -> {
                    DataFX.xOffset = DataFX.stage.getX() - event.getScreenX();
                    DataFX.yOffset = DataFX.stage.getY() - event.getScreenY();
                });
                scene.setOnMouseDragged(event -> {
                    DataFX.stage.setX(event.getScreenX() + DataFX.xOffset);
                    DataFX.stage.setY(event.getScreenY() + DataFX.yOffset);
                });
            }
        }
        public static void setAllTransparent(){
            for(Object[] objects : scenesMap.values()) {
                ((AnchorPane)objects[0]).getScene().setFill(Color.TRANSPARENT);
            }
        }

        public static AnchorPane getAnchorPane(Class controller){
            return ((AnchorPane)scenesMap.get(controller)[0]);
        }
    }

    public static void updateGuiNick(String nick) {
        String greeting = "HELLO " + nick.toUpperCase();
        for(Object[] objects : Scenes.scenesMap.values()) {
            ((AbstractController)((AnchorPane)objects[0]).getUserData()).getHelloLabel().setText(greeting);
        }
    }

    public static void close() {
        if(ClientMessenger.getChannel() != null) {
            try {
                ClientMessenger.getClientMessenger().sendPacket(new ADisconnectPacket().setReason("Closed chat."));
                Thread.sleep(1000);
                ClientMessenger.close();
            } catch (Exception e) {e.printStackTrace();}
        }
        stage.close();
        System.exit(0);
    }
}
