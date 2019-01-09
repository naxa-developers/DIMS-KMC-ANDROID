package np.com.naxa.iset.disasterinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class HazardListModel implements Parcelable {
    String title;
    String desc;
    String image;
    String before_incident;
    String during_incident;
    String after_incident;

    public HazardListModel(String title) {
        this.title = title;
    }
    public HazardListModel(String title, String desc, String image, String before_incident, String during_incident, String after_incident) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.before_incident = before_incident;
        this.during_incident = during_incident;
        this.after_incident = after_incident;
    }

    protected HazardListModel(Parcel in) {
        title = in.readString();
        desc = in.readString();
        image = in.readString();
        before_incident = in.readString();
        during_incident = in.readString();
        after_incident = in.readString();
    }

    public HazardListModel() {
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(image);
        dest.writeString(before_incident);
        dest.writeString(during_incident);
        dest.writeString(after_incident);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HazardListModel> CREATOR = new Creator<HazardListModel>() {
        @Override
        public HazardListModel createFromParcel(Parcel in) {
            return new HazardListModel(in);
        }

        @Override
        public HazardListModel[] newArray(int size) {
            return new HazardListModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBefore_incident() {
        return before_incident;
    }

    public void setBefore_incident(String before_incident) {
        this.before_incident = before_incident;
    }

    public String getDuring_incident() {
        return during_incident;
    }

    public void setDuring_incident(String during_incident) {
        this.during_incident = during_incident;
    }

    public String getAfter_incident() {
        return after_incident;
    }

    public void setAfter_incident(String after_incident) {
        this.after_incident = after_incident;
    }
}
