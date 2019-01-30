package np.com.naxa.iset.mycircle.registeruser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import np.com.naxa.iset.mycircle.MyCircleContactListData;

public class UserModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("get_notification")
    @Expose
    private String getNotification;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("my_circle")
    @Expose
    private List<MyCircleContactListData> myCircle;
    @SerializedName("token")
    @Expose
    private String token;

    public UserModel(String email, String mobileNo, String fullName, String imageUrl, String getNotification, String bloodGroup, String address, List<MyCircleContactListData> myCircle, String token) {
        this.email = email;
        this.mobileNo = mobileNo;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.getNotification = getNotification;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.myCircle = myCircle;
        this.token = token;
    }

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGetNotification() {
        return getNotification;
    }

    public void setGetNotification(String getNotification) {
        this.getNotification = getNotification;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MyCircleContactListData> getMyCircle() {
        return myCircle;
    }

    public void setMyCircle(List<MyCircleContactListData> myCircle) {
        this.myCircle = myCircle;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}