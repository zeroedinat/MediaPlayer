package MediaPlayerMVC;

import java.io.Serializable;

/**
 * Created by Jonathan on 8/29/2016.
 *
 * This class will contain the necessary information for a file being
 * used in the MediaPlayer. It also makes use of the Comparable and Serializable
 * interfaces so that the class can be used in Collections and be written to a
 * binary file.
 */
public class MediaFile implements Comparable<MediaFile>, Serializable
{
    protected String type;

    protected String name;

    protected String absolutePath;

    /**
     * This constructor will take in values for the extension, name, and
     * absolute path for a given file.
     *
     * @param type          The extension of the given file.
     * @param name          The name of the given file.
     * @param absolutePath  The absolute path of the given file.
     */
    public MediaFile(String type, String name, String absolutePath)
    {
        this.type = type;
        this.name = name;
        this.absolutePath = absolutePath;
    }

    /**
     * This method overrides the compareTo method to ensure that any sorting done
     * with MediaFiles is done according to the name property.
     *
     * @param otherFile The other MediaFile object being used for comparison.
     *
     * @return          The int result of the comparison.
     */
    @Override
    public int compareTo(MediaFile otherFile)
    {
        return this.name.compareTo(otherFile.name);
    }
}
