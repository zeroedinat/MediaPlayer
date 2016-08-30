package MediaPlayerMVC;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Jonathan on 8/29/2016.
 */
public class MediaPlayerModel {
    protected MediaPlayer player;

    private boolean loop;

    private boolean shuffle;

    public MediaPlayerModel(Media media)
    {
        player = new MediaPlayer(media);
        loop = false;
        shuffle = false;
    }

    public MediaPlayer.Status getPlayerStatus()
    {
        return player.getStatus();
    }

    public boolean isLooping()
    {
        return loop;
    }

    public boolean isShuffling()
    {
        return shuffle;
    }
}
