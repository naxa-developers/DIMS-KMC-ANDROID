package np.com.naxa.iset.mycircle.registeruser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("full_name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    @SerializedName("blood_group")
    @Expose
    private String blood_group;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("get_notification")
    @Expose
    private String get_notification;

    public UserModel(String token ,String name, String email, String mobile_no, String blood_group, String address, String image_url, String get_notification) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.blood_group = blood_group;
        this.address = address;
        this.image_url = image_url;
        this.get_notification = get_notification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGet_notification() {
        return get_notification;
    }

    public void setGet_notification(String get_notification) {
        this.get_notification = get_notification;
    }
}
