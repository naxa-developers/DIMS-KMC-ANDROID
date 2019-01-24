
package np.com.naxa.iset.mycircle;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCircleContactListData implements Parcelable {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("img_url")
    @Expose
    public String imgUrl;

    @SerializedName("mobile_number")
    @Expose
    public String mobileNumber;

    @SerializedName("registered")
    @Expose
    public Boolean registered;


    protected MyCircleContactListData(Parcel in) {
        name = in.readString();
        imgUrl = in.readString();
        mobileNumber = in.readString();
        byte tmpRegistered = in.readByte();
        registered = tmpRegistered == 0 ? null : tmpRegistered == 1;
    }

    public static final Creator<MyCircleContactListData> CREATOR = new Creator<MyCircleContactListData>() {
        @Override
        public MyCircleContactListData createFromParcel(Parcel in) {
            return new MyCircleContactListData(in);
        }

        @Override
        public MyCircleContactListData[] newArray(int size) {
            return new MyCircleContactListData[size];
        }
    };

    public MyCircleContactListData(String name, String imgUrl, String mobileNumber, Boolean registered) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.mobileNumber = mobileNumber;
        this.registered = registered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imgUrl);
        dest.writeString(mobileNumber);
        dest.writeByte((byte) (registered == null ? 0 : registered ? 1 : 2));
    }
}
