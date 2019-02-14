
package np.com.naxa.iset.publications.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "PublicationsListDetails",
        indices = {@Index(value = "id",
                unique = true)})
public class PublicationsListDetails implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    private String type;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private String id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "summary")
    @SerializedName("summary")
    @Expose
    private String summary;

    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    @Expose
    private String photo;

    @ColumnInfo(name = "file")
    @SerializedName("file")
    @Expose
    private String file;

    @ColumnInfo(name = "videolink")
    @SerializedName("videolink")
    @Expose
    private String videolink;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    public PublicationsListDetails(String type, String id, String title, String summary, String photo, String file, String videolink, String name) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.photo = photo;
        this.file = file;
        this.videolink = videolink;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pid);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.summary);
        dest.writeString(this.photo);
        dest.writeString(this.file);
        dest.writeString(this.videolink);
        dest.writeString(this.name);
    }

    protected PublicationsListDetails(Parcel in) {
        this.pid = in.readInt();
        this.type = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.summary = in.readString();
        this.photo = in.readString();
        this.file = in.readString();
        this.videolink = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<PublicationsListDetails> CREATOR = new Parcelable.Creator<PublicationsListDetails>() {
        @Override
        public PublicationsListDetails createFromParcel(Parcel source) {
            return new PublicationsListDetails(source);
        }

        @Override
        public PublicationsListDetails[] newArray(int size) {
            return new PublicationsListDetails[size];
        }
    };
}
