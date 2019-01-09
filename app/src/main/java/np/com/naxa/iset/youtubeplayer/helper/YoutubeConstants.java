package np.com.naxa.iset.youtubeplayer.helper;

import java.util.ArrayList;

public class YoutubeConstants {
    public static final String API_KEY = "AIzaSyCCzaoezyfmCz25Cmu89356gLzjNb69mmU";
    public static final String VIDEO_KEY = "videoId";

    public static final ArrayList<String> videoList = new ArrayList<String>();
    public static ArrayList<String> getVideoList(){

        videoList.add("3AtDnEC4zak");
        videoList.add("3AtDnEC4zak");
        videoList.add("3AtDnEC4zak");
        videoList.add("3AtDnEC4zak");
        videoList.add("3AtDnEC4zak");
        videoList.add("3AtDnEC4zak");

        return videoList;
    }

    public String getVideoThumbnail (String videoId){
        String thumbnail = "" ;
        thumbnail = "https://img.youtube.com/vi/"+videoId+"/0.jpg";
        return thumbnail;
    }

}
