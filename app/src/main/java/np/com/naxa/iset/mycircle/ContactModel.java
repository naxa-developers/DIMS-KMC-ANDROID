package np.com.naxa.iset.mycircle;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ContactModel")
public class ContactModel {

    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    public String name;

    @ColumnInfo(name = "mobile_no")
    @SerializedName("mobile_no")
    @Expose
    public String mobileNumber;

    @ColumnInfo(name = "img_url")
    @SerializedName("img_url")
    @Expose
    public String img_url;

    @ColumnInfo(name = "registered")
    @SerializedName("registered")
    @Expose
    public int registered;

    @ColumnInfo(name = "add_to_circle")
    @SerializedName("add_to_circle")
    @Expose
    public int addToCircle;

    @SerializedName("token")
    @Expose
    public String token;


    public ContactModel(String name, String mobileNumber, String img_url, int registered, int addToCircle, String token) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.img_url = img_url;
        this.registered = registered;
        this.addToCircle = addToCircle;
        this.token = token;
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

//    public Bitmap getPhoto_name() {
//        return photo;
//    }
//
//    public void setPhoto_name(Bitmap photo) {
//        this.photo = photo;
//    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAddToCircle() {
        return addToCircle;
    }

    public void setAddToCircle(int addToCircle) {
        this.addToCircle = addToCircle;
    }
}
