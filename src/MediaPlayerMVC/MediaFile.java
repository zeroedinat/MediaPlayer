package MediaPlayerMVC;

import java.io.Serializable;

/**
 * Created by Jonathan on 8/30/2016.
 */
public class MediaFile implements Comparable<MediaFile>, Serializable
{
    protected String type;

    protected String name;

    protected String absolutePath;

    public MediaFile(String type, String name, String absolutePath)
    {
        this.type = type;
        this.name = name;
        this.absolutePath = absolutePath;
    }


    @Override
    public int compareTo(MediaFile otherFile)
    {
        return this.name.compareTo(otherFile.name);
    }
}
