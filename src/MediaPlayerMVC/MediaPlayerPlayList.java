package MediaPlayerMVC;

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by Jonathan Oler on 8/29/2016.
 *
 * This class will contain an instance of a MediaFile playlist,
 * it will also handle any iteration, shuffling, or looping of
 * the playlist.
 */
public class MediaPlayerPlayList
{
    private boolean loop;
    private boolean shuffle;
    private boolean skippedForward;
    private boolean skippedBackward;

    private LinkedList<MediaFile> playList;

    private ListIterator<MediaFile> playListIterator;

    /**
     * This constructor will take in the a absolute path name
     * for a file containing a playlist and start to load in
     * all media files contained in the file.
     *
     * @param playListPath The absolute path of the file containing the playlist
     */
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

    /**
     * This method will return the boolean value of whether or not the playlist
     * will loop.
     *
     * @return  The boolean value of the loop property.
     */
    public boolean getLoop()
    {
        return loop;
    }

    /**
     * This method will set the boolean value of whether or not the playlist will
     * loop.
     *
     * @param isLooping The boolean value of the loop property.
     */
    public void setLoop(boolean isLooping)
    {
        loop = isLooping;
    }

    /**
     * This method will return the boolean value of whether or not the playlist should be
     * shuffled.
     *
     * @return  The boolean value of the shuffle property.
     */
    public boolean getShuffle()
    {
        return shuffle;
    }

    /**
     * This method will set the boolean value for whether or not the playlist should be
     * shuffled.
     *
     * @param isShuffling   The new value for the shuffle property.
     */
    public void setShuffle(boolean isShuffling)
    {
        shuffle = isShuffling;
    }

    /**
     * This method will return the playlist object.
     *
     * @return   The LinkedList of MediaFiles that is being used as the playlist.
     */
    public LinkedList<MediaFile> getPlayList()
    {
        return playList;
    }

    /**
     * This method will take in a absolute path for a file containing a playlist and
     * read all the MediaFile objects contained in it and store them into the playlist.
     *
     * @param playListPath The absolute path of the file containing the playlist
     */
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

    /**
     * This method will wrap the playlist ListIterator's next functionality and compensates for
     * the issue of calling next right after a call to previous.
     *
     * @return The next MediaFile in the playlist.
     *
     * @throws NoSuchElementException   If there isn't a next MediaFile in the playlist.
     */
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

    /**
     * This method will wrap the playlist ListIterator's hasNext functionality and compensates for
     * the case in which the playlist should be able to loop.
     *
     * @return The boolean value of whether or not there is a next element in the playlist.
     */
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

    /**
     * This method will wrap the playlist ListIterator's previous functionality and compensates
     * for the issue of calling previous right after a call to next.
     *
     * @return  The previous MediaFile in the playlist.
     * @throws NoSuchElementException   If there isn't a previous MediaFile in the playlist.
     */
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

    /**
     * This method will wrap the playlist ListIterator's hasPrevious functionality. It also compensates
     * for the issue of checking for a previous right after making a call to next that resulted in
     * restarting the playlist.
     *
     * @return The boolean value of whether or not there is a previous MediaFile in the playlist.
     */
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

    /**
     * This method will restart the playlist's iterator to be
     * back at the beginning of the playlist.
     */
    public void restart()
    {
        playListIterator = playList.listIterator();
    }
}
