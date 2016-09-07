package MediaPlayerMVC;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MediaPlayerController implements Initializable
{
    public enum PlayerStatus { READY, PLAYING, PAUSED, STOPPED }

    @FXML private HBox display;
    @FXML private ImageView playButton;
    @FXML private ImageView nextButton;
    @FXML private ImageView previousButton;

    private ArrayList<String> playListOptions;
    private MediaPlayer player;
    private MediaPlayerPlayList playList;
    private MediaView mediaView;
    private PlayerStatus status;

    private final String USER_DIR = "file:///" + (new File("").getAbsolutePath().replace("\\", "/"));
    private final Image FEELS_GOOD_MAN = new Image(USER_DIR + "/Images/feelsgoodman.jpeg");
    private final Image PEPEPLS = new Image(USER_DIR + "/Images/pepepls.gif");
    private final Image PAUSE = new Image(USER_DIR + "/Images/Pause.png");
    private final Image PLAY = new Image(USER_DIR + "/Images/play.png");

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        playListOptions = new ArrayList();

        File tempFile = new File(System.getProperty("user.dir").replace("\\", "/") + "/PlayLists");

        File[] files = tempFile.listFiles((directory, fileName) -> {
            return fileName.toLowerCase().endsWith(".bin");
        });

        for(File file : files)
        {
           playListOptions.add(file.getAbsolutePath());
        }

        playList = new MediaPlayerPlayList(playListOptions.get(0));
        player = new MediaPlayer(new Media("file:///" + playList.next().absolutePath));

        mediaView = new MediaView();

        display.getChildren().addAll(new ImageView(FEELS_GOOD_MAN), new ImageView(FEELS_GOOD_MAN));

        playButton.setImage(PLAY);

        nextButton.setImage(new Image(USER_DIR + "/Images/next.png"));
        previousButton.setImage(new Image(USER_DIR + "/Images/previous.png"));

        status = PlayerStatus.PAUSED;
    }

    @FXML protected void playButtonAction(ActionEvent event)
    {
        if(status == PlayerStatus.PLAYING)
        {
            status = PlayerStatus.PAUSED;
            changeDisplay();
            player.pause();
            return;
        }

        status = PlayerStatus.PLAYING;
        changeDisplay();
        player.play();
        createPlayingService().start();

        event.consume();
    }

    @FXML protected void previousButtonAction(ActionEvent event)
    {
        boolean resumeAfter = status == PlayerStatus.PLAYING;

        player.stop();
        status = PlayerStatus.STOPPED;

        if(playList.hasPrevious() && player.getCurrentTime().lessThan(Duration.millis(10000)))
        {
            player = new MediaPlayer(new Media("file:///" + playList.previous().absolutePath));
            status = PlayerStatus.READY;
        }

        if(resumeAfter)
        {
            status = PlayerStatus.PLAYING;
            changeDisplay();
            player.play();
            createPlayingService().start();
        }
        else
        {
            changeDisplay();
        }

        event.consume();
    }

    @FXML protected void nextButtonAction(ActionEvent event)
    {
        boolean resumeAfter = status == PlayerStatus.PLAYING;

        player.stop();
        status = PlayerStatus.STOPPED;

        if(playList.hasNext())
        {
            player = new MediaPlayer(new Media("file:///" + playList.next().absolutePath));
            status = PlayerStatus.READY;
        }
        else
        {
            playList.restart();
            player = new MediaPlayer(new Media("file:///" + playList.next().absolutePath));
            status = PlayerStatus.READY;
            resumeAfter = false;
        }

        if(resumeAfter)
        {
            status = PlayerStatus.PLAYING;
            changeDisplay();
            player.play();
            createPlayingService().start();
        }
        else
        {
            changeDisplay();
        }

        event.consume();
    }

    protected void changeDisplay()
    {
        switch (status)
        {
            case PLAYING :
            {
                display.getChildren().clear();

                if(player.getMedia().getSource().toLowerCase().endsWith(".mp4"))
                {
                    mediaView.setMediaPlayer(player);
                    display.getChildren().add(mediaView);
                }
                else
                {
                    display.getChildren().addAll(new ImageView(PEPEPLS), new ImageView(PEPEPLS));
                }

                playButton.setImage(PAUSE);

                break;
            }
            default :
            {
                display.getChildren().clear();

                display.getChildren().addAll(new ImageView(FEELS_GOOD_MAN), new ImageView(FEELS_GOOD_MAN));
                playButton.setImage(PLAY);
            }
        }
    }

    private Service<Void> createPlayingService()
    {
        return new Service<Void>()
        {
            @Override
            protected Task<Void > createTask()
            {
                return new Task<Void>()
                {
                    @Override
                    protected Void call()throws Exception
                    {
                        while(status == PlayerStatus.PLAYING)
                        {
                            if(player.getCurrentTime().greaterThanOrEqualTo(player.getStopTime()))
                            {
                                final CountDownLatch latch = new CountDownLatch(1);
                                Platform.runLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        try
                                        {
                                            latch.await();
                                            changeDisplay();
                                        } catch (InterruptedException ex)
                                        {
                                            status =  PlayerStatus.STOPPED;
                                            changeDisplay();
                                            player.stop();
                                        }
                                    }
                                });

                                if(playList.hasNext())
                                {
                                    System.out.println("hey");
                                    player.stop();
                                    player = new MediaPlayer(new Media("file:///" + playList.next().absolutePath));
                                    status = PlayerStatus.PLAYING;
                                    latch.countDown();
                                    player.play();
                                }
                                else
                                {
                                    status = PlayerStatus.READY;
                                    latch.countDown();
                                    playList.restart();
                                    player.stop();
                                    player = new MediaPlayer(new Media("file:///" + playList.next().absolutePath));
                                }
                            }
                        }

                        return null;
                    }
                };

            }
        };
    }
}
