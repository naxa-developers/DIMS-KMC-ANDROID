package np.com.naxa.iset.mycircle;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ContactModel")
public class ContactModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    public String id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    public String name;

    @ColumnInfo(name = "mobile_no")
    @SerializedName("mobile_no")
    @Expose
    public String mobileNumber;

//    @ColumnInfo(name = "photo")
//    @SerializedName("photo")
//    @Expose
//    public Bitmap photo;

    @ColumnInfo(name = "photo_url")
    @SerializedName("photo_url")
    @Expose
    public String photoURI;


    @ColumnInfo(name = "add_to_circle")
    @SerializedName("add_to_circle")
    @Expose
    public int addToCircle;



    public ContactModel(String id, String name, String mobileNumber, String photoURI, int addToCircle) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
//        this.photo = photo;
        this.photoURI = photoURI;
        this.addToCircle = addToCircle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

//    public Bitmap getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(Bitmap photo) {
//        this.photo = photo;
//    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mobileNumber);
//        dest.writeParcelable(this.photo, flags);
        dest.writeString(this.photoURI);
        dest.writeInt(this.addToCircle);
    }

    public ContactModel() {
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.mobileNumber = in.readString();
//        this.photo = in.readParcelable(Bitmap.class.getClassLoader());
        this.photoURI = in.readString();
        this.addToCircle = in.readInt();
    }

    public static final Parcelable.Creator<ContactModel> CREATOR = new Parcelable.Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel source) {
            return new ContactModel(source);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
