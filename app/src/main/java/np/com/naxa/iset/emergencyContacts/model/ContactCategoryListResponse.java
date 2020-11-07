
package np.com.naxa.iset.emergencyContacts.model;

import androidx.room.Entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactCategoryListResponse {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ContactCategoryListDetails> data = null;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContactCategoryListDetails> getData() {
        return data;
    }

    public void setData(List<ContactCategoryListDetails> data) {
        this.data = data;
    }

}
