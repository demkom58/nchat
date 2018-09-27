package com.demkom58.nchat.client.util;

import com.demkom58.nchat.client.gui.AbstractController;
import com.demkom58.nchat.client.network.ClientMessenger;
import com.demkom58.nchat.common.network.packets.common.ADisconnectPacket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class DataFX {
    public static Stage stage;
    public static double xOffset;
    public static double yOffset;

    public static class Scenes {
        private static final Map<Class<? extends AbstractController>, Object[]> SCENES_MAP = new HashMap<>();

        public static Scene getScene(Class<? extends AbstractController> controller) {
            return getAnchorPane(controller).getScene();
        }

        public static void addScene(Class<? extends AbstractController> controller, FXMLLoader loader) throws Exception {
            if(SCENES_MAP.containsKey(controller)) return;

            AnchorPane anchorPane = loader.load();
            anchorPane.setUserData(loader.getController());
            ((AbstractController)loader.getController()).setScene(new Scene(loader.getRoot()));

            SCENES_MAP.put(controller, new Object[]{ anchorPane, loader });
        }

        public static void removeScene(Class<? extends AbstractController> controller) {
            SCENES_MAP.remove(controller);
        }

        public static FXMLLoader getLoader(Class<? extends AbstractController> controller){
            return (FXMLLoader) SCENES_MAP.get(controller)[1];
        }

        public static AbstractController getController(Class<? extends AbstractController> controller){
            return (AbstractController)getAnchorPane(controller).getUserData();
        }

        public static void setAllDraggable(){
            for(Object[] sceneData : SCENES_MAP.values()) {
                final Scene scene = ((AnchorPane)sceneData[0]).getScene();

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
            for(Object[] objects : SCENES_MAP.values()) {
                ((AnchorPane)objects[0]).getScene().setFill(Color.TRANSPARENT);
            }
        }

        public static AnchorPane getAnchorPane(Class controller){
            return ((AnchorPane) SCENES_MAP.get(controller)[0]);
        }
    }

    public static void updateGuiNick(String nick) {
        final String greeting = "HELLO " + nick.toUpperCase();
        for(Object[] objects : Scenes.SCENES_MAP.values())
            ((AbstractController)((AnchorPane)objects[0]).getUserData()).getHelloLabel().setText(greeting);
    }

    public static void close() {
        if(ClientMessenger.getChannel() != null) {
            try {
                ClientMessenger.getClientMessenger().sendPacket(new ADisconnectPacket().setReason("Closed chat."));
                Thread.sleep(1000);
                ClientMessenger.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
        stage.close();
        System.exit(0);
    }

}
