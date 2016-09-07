package MediaPlayerMVC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by Jonathan Oler on 8/29/2016.
 *
 * This class is the main starting point of the application and
 * will just define the primary GUI stage and link an FXML and CSS file to
 * it.
 */
public class MediaPlayerModel extends Application
{
    /**
     * This method will define the primary GUI stage and link an FXML and CSS file to it.
     * @param primaryStage The primary stage for the GUI
     * @throws Exception   Any uncaught exceptions that occur during the running of the application will be thrown.
     */
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

    /**
     * This method is only necessary in instances where the start method cannot be called
     * at the start of execution.
     * @param args Any arguments needed to process the launching of the application.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
