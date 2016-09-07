package MediaPlayerMVC;

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by Jonathan on 8/29/2016.
 */
public class MediaPlayerPlayList
{
    private boolean loop;
    private boolean shuffle;
    private boolean skippedForward;
    private boolean skippedBackward;

    private LinkedList<MediaFile> playList;

    private ListIterator<MediaFile> playListIterator;

    public MediaPlayerPlayList(String playListPath)
    {
        playList = new LinkedList();

        setPlayList(playListPath);

        playListIterator = playList.listIterator();

        loop = false;
        shuffle = false;
        skippedForward = false;
        skippedBackward = false;
    }

    public boolean getLoop()
    {
        return loop;
    }

    public void setLoop(boolean isLooping)
    {
        loop = isLooping;
    }

    public boolean getShuffle()
    {
        return shuffle;
    }

    public void setShuffle(boolean isShuffling)
    {
        shuffle = isShuffling;
    }

    public LinkedList<MediaFile> getPlayList()
    {
        return playList;
    }

    public void setPlayList(String playListPath)
    {
        playList.clear();

        try
        {
            FileInputStream fis = new FileInputStream(playListPath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while(true)
            {
                playList.add((MediaFile) ois.readObject());
            }
        }
        catch(IOException| ClassNotFoundException ex)
        {
            // The end of the play list was reached
        }
    }

    public MediaFile next() throws NoSuchElementException
    {
        if(skippedBackward)
        {
            skippedBackward = false;
            playListIterator.next();
        }

        skippedForward = true;
        return playListIterator.next();
    }

    public boolean hasNext()
    {
        boolean hasNext = playListIterator.hasNext();

        if(!hasNext && loop)
        {
            hasNext = true;
            playListIterator = playList.listIterator();
        }

        return hasNext;
    }

    public MediaFile previous() throws NoSuchElementException
    {
        if(skippedForward)
        {
            skippedForward = false;
            playListIterator.previous();
        }

        skippedBackward = true;
        return playListIterator.previous();
    }

    public boolean hasPrevious()
    {
        boolean hasPrevious = playListIterator.hasPrevious();

        if(hasPrevious && skippedForward)
        {
            playListIterator.previous();
            hasPrevious = playListIterator.hasPrevious();
            playListIterator.next();
        }

        return hasPrevious;
    }

    public void restart()
    {
        playListIterator = playList.listIterator();
    }
}
