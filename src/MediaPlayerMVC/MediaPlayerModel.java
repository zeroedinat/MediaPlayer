package MediaPlayerMVC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MediaPlayerModel extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane pane = FXMLLoader.load(getClass().getResource("MediaPlayerView.fxml"));
        Scene scene = new Scene(pane);

        scene.getStylesheets().add("file:///" + System.getProperty("user.dir").replace("\\", "/")  + "/StyleSheets/MediaPlayerStyle.css");

        primaryStage.setTitle("Pepe's Amazing Media Player");
        primaryStage.getIcons().add(new Image("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/Images/feelsgoodman.jpeg"));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
