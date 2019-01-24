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

//    @ColumnInfo(name = "id")
//    @SerializedName("id")
//    @Expose
//    public String id;

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

    @ColumnInfo(name = "registered")
    @SerializedName("registered")
    @Expose
    private Boolean registered;



    public ContactModel( String name, String mobileNumber, String photoURI, int addToCircle, boolean registered) {
//        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
//        this.photo = photo;
        this.photoURI = photoURI;
        this.addToCircle = addToCircle;
        this.registered = registered;
    }

//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }

    protected ContactModel(Parcel in) {
        cid = in.readInt();
        name = in.readString();
        mobileNumber = in.readString();
        photoURI = in.readString();
        addToCircle = in.readInt();
        byte tmpRegistered = in.readByte();
        registered = tmpRegistered == 0 ? null : tmpRegistered == 1;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cid);
        dest.writeString(name);
        dest.writeString(mobileNumber);
        dest.writeString(photoURI);
        dest.writeInt(addToCircle);
        dest.writeByte((byte) (registered == null ? 0 : registered ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel in) {
            return new ContactModel(in);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };

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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }
}
