
package np.com.naxa.iset.emergencyContacts.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ContactCategoryListDetails",
        indices = {@Index(value = {"id"},
                unique = true)})
public class ContactCategoryListDetails {

    @PrimaryKey(autoGenerate = true)
    private int cid;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private String id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    private String name;

    @SerializedName("name_id")
    @ColumnInfo(name = "name_id")
    @Expose
    private String nameId;

    @SerializedName("category")
    @ColumnInfo(name = "category")
    @Expose
    private String category;

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

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
