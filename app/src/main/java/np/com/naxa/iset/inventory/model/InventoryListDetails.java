
package np.com.naxa.iset.inventory.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "InventoryListDetails",
        indices = {@Index(value = {"id"},
                unique = true)})
public class InventoryListDetails implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int iid;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orgname")
    @Expose
    private String orgname;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("contactperson")
    @Expose
    private String contactperson;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subcat")
    @Expose
    private String subcat;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_slug")
    @Expose
    private String categorySlug;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("subcat_name")
    @Expose
    private String subcatName;
    @SerializedName("sub_cat_slug")
    @Expose
    private String subCatSlug;

    public InventoryListDetails(String id, String orgname, String address, String phone, String contactperson, String email, String language, String category, String subcat, String categoryName, String categorySlug, String categoryImage, String subcatName, String subCatSlug) {
        this.id = id;
        this.orgname = orgname;
        this.address = address;
        this.phone = phone;
        this.contactperson = contactperson;
        this.email = email;
        this.language = language;
        this.category = category;
        this.subcat = subcat;
        this.categoryName = categoryName;
        this.categorySlug = categorySlug;
        this.categoryImage = categoryImage;
        this.subcatName = subcatName;
        this.subCatSlug = subCatSlug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getSubcatName() {
        return subcatName;
    }

    public void setSubcatName(String subcatName) {
        this.subcatName = subcatName;
    }

    public String getSubCatSlug() {
        return subCatSlug;
    }

    public void setSubCatSlug(String subCatSlug) {
        this.subCatSlug = subCatSlug;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.iid);
        dest.writeString(this.id);
        dest.writeString(this.orgname);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.contactperson);
        dest.writeString(this.email);
        dest.writeString(this.language);
        dest.writeString(this.category);
        dest.writeString(this.subcat);
        dest.writeString(this.categoryName);
        dest.writeString(this.categorySlug);
        dest.writeString(this.categoryImage);
        dest.writeString(this.subcatName);
        dest.writeString(this.subCatSlug);
    }

    protected InventoryListDetails(Parcel in) {
        this.iid = in.readInt();
        this.id = in.readString();
        this.orgname = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.contactperson = in.readString();
        this.email = in.readString();
        this.language = in.readString();
        this.category = in.readString();
        this.subcat = in.readString();
        this.categoryName = in.readString();
        this.categorySlug = in.readString();
        this.categoryImage = in.readString();
        this.subcatName = in.readString();
        this.subCatSlug = in.readString();
    }

    public static final Parcelable.Creator<InventoryListDetails> CREATOR = new Parcelable.Creator<InventoryListDetails>() {
        @Override
        public InventoryListDetails createFromParcel(Parcel source) {
            return new InventoryListDetails(source);
        }

        @Override
        public InventoryListDetails[] newArray(int size) {
            return new InventoryListDetails[size];
        }
    };

    public int getIid() {

        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }
}
