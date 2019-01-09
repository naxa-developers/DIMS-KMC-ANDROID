package np.com.naxa.iset.mycircle;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {
    public String id;
    public String name;
    public String mobileNumber;
    public Bitmap photo;
    public Uri photoURI;

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

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Uri getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(Uri photoURI) {
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
        dest.writeParcelable(this.photo, flags);
        dest.writeParcelable(this.photoURI, flags);
    }

    public ContactModel() {
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.mobileNumber = in.readString();
        this.photo = in.readParcelable(Bitmap.class.getClassLoader());
        this.photoURI = in.readParcelable(Uri.class.getClassLoader());
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
}
