package np.com.naxa.iset.youtubeplayer.helper;

public class YoutubeDataModel {

    String videoId;
    String videoTitle;
    String videoDesc;
    String videoThumbnail;

    public YoutubeDataModel(String videoId, String videoTitle, String videoDesc, String videoThumbnail) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoDesc = videoDesc;
        this.videoThumbnail = videoThumbnail;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }
}
