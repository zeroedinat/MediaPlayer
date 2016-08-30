package MediaPlayerMVC;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayerController implements Initializable
{
    @FXML private ImageView pepe1;
    @FXML private ImageView pepe2;
    @FXML private ImageView playButton;
    @FXML private ImageView nextButton;
    @FXML private ImageView previousButton;
    @FXML private MediaView mediaView;

    private MediaPlayerModel model;

    private String userDir;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        userDir = "file:///" + (new File("").getAbsolutePath().replace("\\", "/"));
        Media media = new Media(userDir + "//Playlists//She.mp3");
        model = new MediaPlayerModel(media);
        mediaView = new MediaView(model.player);

        pepe1.setImage(new Image(userDir + "//Images//feelsgoodman.jpeg"));
        pepe2.setImage(new Image(userDir + "//Images//feelsgoodman.jpeg"));

        playButton.setImage(new Image(userDir + "//Images//play.png"));
        nextButton.setImage(new Image(userDir + "//Images//next.png"));
        previousButton.setImage(new Image(userDir + "//Images//previous.png"));

        playButton.setPreserveRatio(true);
        nextButton.setPreserveRatio(true);
        previousButton.setPreserveRatio(true);
    }

    @FXML protected void playButtonAction(ActionEvent event)
    {
        if(model.getPlayerStatus() != MediaPlayer.Status.PLAYING)
        {
            model.player.play();
            pepe1.setImage(new Image(userDir + "//Images//pepepls.gif"));
            pepe2.setImage(new Image(userDir + "//Images//pepepls.gif"));
            playButton.setImage(new Image(userDir + "//Images//stop.png"));
        }
        else
        {
            model.player.pause();
            pepe1.setImage(new Image(userDir + "//Images//feelsgoodman.jpeg"));
            pepe2.setImage(new Image(userDir + "//Images//feelsgoodman.jpeg"));
            playButton.setImage(new Image(userDir + "//Images//play.png"));
        }

        event.consume();
    }

    @FXML protected void previousButtonAction(ActionEvent event)
    {

    }

    @FXML protected void nextButtonAction(ActionEvent event)
    {

    }
}
